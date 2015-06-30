<%-- 
    Document   : template
    Created on : May 17, 2014, 5:06:42 PM
    Author     : roberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            

//Now let's remove the bullets and the margins and padding from the list:



        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/styles.css" rel="stylesheet" type="text/css" >
        <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
        <script type="text/javascript">
            function changeDiv(link)
            {
                var contentDiv = $('#content-container');
                if (link == '1')
                    contentDiv.load("createUser.jsp");
                else if (link == '2')
                    contentDiv.load("login.jsp");
                else if (link == '3')
                    contentDiv.load("play.jsp");
                else if (link == '4')
                    contentDiv.load("logOut.jsp");
                else if (link == '5')
                    contentDiv.load("viewGame.jsp");
            }
        </script>
    </head>
    <body>
        <div id="outer-container">  
            <div id="header">  
                <h1>Connect Four Game</h1>  
            </div>  
            <div style="clear: both">  
            </div>  

            <div id="top-Nav">
                <ul> 
                <li class="menu-item"><a id="link1" onclick="changeDiv('1')">&nbsp;&nbsp;CreateUser</a></li>
                <li class="menu-item"><a id="link2" onclick="changeDiv('2')">&nbsp;&nbsp;Login</a></li>
                <li class="menu-item"><a id="link3" onclick="changeDiv('3')">&nbsp;&nbsp;Play</a></li>
                <li class="menu-item"><a id="link4" onclick="changeDiv('4')">&nbsp;&nbsp;Logout</a></li>
                </ul>
                
            </div>
            <div style="clear: both">  
            </div>  
            <div id="content-container">  
                <jsp:include page="createUser.jsp" />
            </div>  
            
            <div style="clear: both">  
            </div>  

            <div id="footer">  
                <h1>Rodrigo Amaducci Szwarc, Daniel Garduño Hernandez, Mario Garcia Roque</h1>  
            </div>  
        </div> 
    </body>
</html>