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
        <title>Test Webservers</title>
        <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#playSubmit').on('click', function(e) {
                    e.preventDefault(); //prevent default action
                    var isCheckedFirstPlayer = $('#firstPlayer').prop('checked');
//                    var isCheckedPosition = $('#position').prop('checked');
                    $.ajax({
                        url: 'playAction',
                        type: 'POST',
                        data: 'firstPlayer=' + isCheckedFirstPlayer,
//                        data: 'computer=' + isCheckedComputer + '&position=' + isCheckedPosition,
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
            <h1>Play</h1>
            <table border="0">
                <tbody>
                    <tr>
                        <td><input id="firstPlayer"   type="checkbox" name="firstPlayer" value="firstPlayer" /></td>
                        <td>Select if you want to be first player</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input id="playSubmit" type="submit" value="play" /></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
