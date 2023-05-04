package com.arman_jaurigue.data_access.websocket;

import com.arman_jaurigue.data_objects.Stop;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.data_objects.endpoint.Message;
import com.arman_jaurigue.data_objects.endpoint.StopApproval;
import com.arman_jaurigue.data_objects.endpoint.ViewingUserChanged;
import com.arman_jaurigue.logic_layer.MasterManager;

import javax.servlet.http.HttpSession;
import javax.swing.text.View;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/stopslive/{planId}",encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class}, configurator = GetHttpSessionConfigurator.class)
public class MessageEndpoint {
    private static final SortedMap<String, Set<Session>> planViewers;
    private static final Map<Session, User> relatedUsers;

    static
    {
        planViewers = Collections.synchronizedSortedMap(new TreeMap<>());
        relatedUsers = Collections.synchronizedMap(new HashMap<>());
    }

    public static Set<Session> getPlanViewers(int planId) {
        return planViewers.get(Integer.toString(planId));
    }

    public static User getRelatedUser(Session session) {
        return relatedUsers.get(session);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("planId") String planId, EndpointConfig config) throws EncodeException, IOException {
        if (!planViewers.containsKey(planId))
        {
            final Set<Session> planSessions = Collections.synchronizedSet(new HashSet<>());
            planViewers.put(planId, planSessions);
        }

        HttpSession relatedSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User)relatedSession.getAttribute("user");
        boolean containsUser = false;
        for (Session sess :
                planViewers.get(planId)) {
            User sessionUser = relatedUsers.get(sess);
            if (sessionUser != null && sessionUser.getId() == user.getId()) {
                containsUser = true;
                break;
            }
        }
        planViewers.get(planId).add(session);
        relatedUsers.put(session, user);
        if (!containsUser) {
            Message message = new Message();
            message.setEventName("viewingUsersChanged");
            ViewingUserChanged viewingUserChangedData = new ViewingUserChanged();
            viewingUserChangedData.setUserId(user.getId());
            viewingUserChangedData.setName(user.getFirstName() + " " + user.getLastName());
            viewingUserChangedData.setJoined(true);
            message.setEventData(viewingUserChangedData.getJson());
            for (Session sess : planViewers.get(planId)) {
                sess.getBasicRemote().sendObject(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("planId") String planId) throws EncodeException, IOException {
        planViewers.get(planId).remove(session);
        if (planViewers.get(planId).isEmpty())
        {
            planViewers.remove(planId);
        }
        User user = relatedUsers.get(session);
        Set<Session> sessions = planViewers.get(planId);

        boolean otherSessions = false;
        if (sessions != null) {
            for (Session sess :
                    planViewers.get(planId)) {
                User sessionUser = relatedUsers.get(sess);
                if (sessionUser.getId() == user.getId()) {
                    otherSessions = true;
                    break;
                }
            }
        }
        if (!otherSessions) {
            if (sessions != null) {
                Message message = new Message();
                message.setEventName("viewingUsersChanged");
                ViewingUserChanged viewingUserChangedData = new ViewingUserChanged();
                viewingUserChangedData.setUserId(user.getId());
                viewingUserChangedData.setName(user.getFirstName() + " " + user.getLastName());
                viewingUserChangedData.setJoined(false);
                message.setEventData(viewingUserChangedData.getJson());
                for (Session sess : sessions) {
                    sess.getBasicRemote().sendObject(message);
                }
            }
        }
        relatedUsers.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, Message message, @PathParam("planId") String planId) throws EncodeException, IOException {
        // Preform logic here and then send response to EVERYONE viewing the plan if it succeeds or just the original session if it fails
        try {
            if (message.getEventName().equals("changeStopApproval"))
            {
                StopApproval approval = new StopApproval(message.getEventData());
                MasterManager.getMasterManager().getStopManager().editStopStatusByStopId(approval.getStopId(), approval.isApproved());
                List<Stop> stops = MasterManager.getMasterManager().getStopManager().getAllStopsByPlanId(Integer.parseInt(planId));
                List<Stop> matchingStops = new ArrayList<>();
                for (Stop stop :
                        stops) {
                    if (stop.getStatus() != null && stop.getStatus() == approval.isApproved()){
                        matchingStops.add(stop);
                    }
                }
                for(int i = 0; i < matchingStops.size(); i++) {
                    if (matchingStops.get(i).getStopId() == approval.getStopId()) {
                        approval.setPosition(i);
                        break;
                    }
                }
                message.setEventData(approval.getJson());
                for(Session subscriber: planViewers.get(planId))
                {
                    subscriber
                            .getBasicRemote()
                            .sendObject(message);
                }
            }
        } catch (Exception ex)
        {

        }
    }

    @OnError
    public void onError(Throwable throwable)
    {
        System.out.println(throwable.getMessage());
    }
}