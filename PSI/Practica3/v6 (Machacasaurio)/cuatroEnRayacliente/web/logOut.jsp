<%-- 
    Document   : logout
    Created on : May 20, 2014, 12:01:01 PM
    Author     : roberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Webservers</title>
        <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#userSubmit').on('click', function(e) {
                    e.preventDefault(); //prevent default action
                    $.ajax({
                        url: 'logoutAction',
                        type: 'POST',
                        success: function(response) {
                            $('#contact_form').html(response);
                        }
                    });
                });
            }); //END $(document).ready()

        </script>
    </head>
    <body>
        <div id="contact_form">
            <h1>Login Out</h1>
            <table border="0">
                <tbody>
                    <tr>
                        <td></td>
                        <td><input id="userSubmit" type="submit" value="logout" /></td>
                    </tr>
                </tbody>
            </table>
            <!--</form>-->
        </div>
    </body>
</html>
