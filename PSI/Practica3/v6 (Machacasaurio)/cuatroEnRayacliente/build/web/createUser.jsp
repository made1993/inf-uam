<%-- 
    Document   : createUser
    Created on : May 16, 2014, 10:29:33 AM
    Author     : roberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test WEbservers</title>
        <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#userSubmit').on('click', function(e) {
                    e.preventDefault(); //prevent default action
                    var passwd = $('#passwd').val();
                    var userName = $('#userName').val();
                    $.ajax({
                        url: 'createUserAccion',
                        type: 'POST',
                        data: 'passwd=' + passwd + '&userName=' + userName,
                        success: function(response) {
                            $('#contact_form').html(response);
                        }
                    });
                });
            }); //END $(document).ready()

        </script>
    </head>
    <body>
        <!-- <a id="link1" onclick="changeDiv('1')">&nbsp;&nbsp;CreateUser</a></li> -->
        <!--<form action="createUserAccion" method="POST">-->
        <div id="contact_form">
            <h1>Create new user</h1>
            <!--<form id="createUserAccion">-->
            <table border="0">
                <tbody>
                    <tr>
                        <td>UserName</td>
                        <td><input id= "userName" type="text" name="userName" value="" /></td>
                    </tr>
                    <tr>
                        <td>Passwd</td>
                        <td><input id= "passwd" type="password" name="passwd" value="" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input id="userSubmit" type="submit" value="createUser" /></td>
                    </tr>
                </tbody>
            </table>
            <!--</form>-->
        </div>
    </body>
</html>