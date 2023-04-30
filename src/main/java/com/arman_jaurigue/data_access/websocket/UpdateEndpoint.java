package com.arman_jaurigue.data_access.websocket;

import com.arman_jaurigue.data_objects.Update;
import com.arman_jaurigue.logic_layer.MasterManager;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/stopslive/{planId}",encoders = {UpdateEncoder.class}, decoders = {UpdateDecoder.class})
public class UpdateEndpoint {
    private static final SortedMap<String, Set<Session>> planViewers;

    static
    {
        planViewers = Collections.synchronizedSortedMap(new TreeMap<>());
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("planId") String planId)
    {
        if (!planViewers.containsKey(planId))
        {
            final Set<Session> planSessions = Collections.synchronizedSet(new HashSet<>());
            planSessions.add(session);
            planViewers.put(planId, planSessions);
        }
        System.out.println("Session Started");
    }

    @OnClose
    public void onClose(Session session, @PathParam("planId") String planId)
    {
        planViewers.get(planId).remove(session);
        System.out.println("Session Ended");
        if (planViewers.get(planId).isEmpty())
        {
            planViewers.remove(planId);
            System.out.println("Plan group empty");
        }
    }

    @OnMessage
    public void onMessage(Session session, Update update, @PathParam("planId") String planId) throws EncodeException, IOException {
        // Preform logic here and then send response to EVERYONE viewing the plan if it succeeds or just the original session if it fails
        try {
            MasterManager.getMasterManager().getStopManager().updateStopApproval(update.getStopId(), update.isApproved());
        } catch (Exception ex)
        {
            session.getBasicRemote().sendObject();
        }
        for(Session subscriber: planViewers.get(planId))
        {
            if (!subscriber.equals(session))
            {
                //changed
                subscriber
                        .getBasicRemote()
                        .sendObject(update);
            }
        }
    }

    @OnError
    public void onError(Throwable throwable)
    {
        System.out.println(throwable.getMessage());
    }
}
