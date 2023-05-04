<%@ tag import="com.arman_jaurigue.servlets.ServletHelper" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="styles" fragment="true" %>
<%@attribute name="scripts" fragment="true" %>
<!DOCTYPE html>
<html>
<head>
    <title><jsp:invoke fragment="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <link rel="stylesheet" href="styles/site.css"/>
    <jsp:invoke fragment="styles"/>
</head>
<body>
    <header>
        <nav class="navbar navbar-dark navbar-expand-sm bg-cosmos">
            <div class="container-fluid">
                <a class="navbar-brand" href="<%=ServletHelper.getBaseUrl(request)%>">Travel Planner</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="<%=ServletHelper.getBaseUrl(request)%>">Home</a>
                        </li>
                        <% if (session.getAttribute("user") != null) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="plans">My Plans</a>
                        </li>
                        <% } %>
                    </ul>


                    <ul class="navbar-nav ml-auto mb-0">
                        <% if (session.getAttribute("user") == null) { %>
                        <a class="nav-link" href="register">Register</a>
                        <% } %>
                            <li class="nav-item">
                            <% if (session.getAttribute("user") != null) { %>
                            <form action="logout" method="post" class="mb-0">
                                <button class="nav-link" >Log Out</button>
                            </form>
                            <% } else { %>
                            <a class="nav-link" href="login">Log In</a>
                            <% } %>
                        </li>
                    </ul>
            </div>
            </div>
        </nav>
    </header>
    <main id="body">
        <jsp:doBody/>
    </main>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <jsp:invoke fragment="scripts"/>
</body>
</html>