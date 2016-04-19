<%--
  Created by IntelliJ IDEA.
  User: Eagle
  Date: 18.4.2016 г.
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>File Downloading Form</title>
</head>
<body>
<h3>Download file:</h3>
Select a file type to download:
<br />
<form action="DownloadServlet" method="POST">
  <select name="fileType">
    <option value="json">Json</option>
    <option value="xls">Excel</option>
    <option value="xml">XML</option>
  </select>

  <br />
  <br />
  <br />
  <input type="submit" value="Download File" />
</form>
</body>
</html>