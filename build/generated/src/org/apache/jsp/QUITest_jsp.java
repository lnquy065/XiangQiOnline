package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class QUITest_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <script src=\"js/QUI.js\" type=\"text/javascript\"></script>\n");
      out.write("        <script src=\"js/jquery-3.2.1.min.js\" type=\"text/javascript\"></script>\n");
      out.write("        <link href=\"css/QUI.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("        <style>\n");
      out.write("            #myAvatarLB {\n");
      out.write("                position: absolute;\n");
      out.write("                top:140px;\n");
      out.write("                right: 95px;\n");
      out.write("                background: white;\n");
      out.write("                width: 20px;\n");
      out.write("                height: 20px;\n");
      out.write("                border-radius: 5px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            #myAvatarLB:hover {\n");
      out.write("                background: blue;\n");
      out.write("                color:white;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            .avatarBtn {\n");
      out.write("                margin-top:5px;\n");
      out.write("                width: 70px;\n");
      out.write("                padding: 5px;\n");
      out.write("                background: white;\n");
      out.write("                border: none;\n");
      out.write("                border-radius:  5px;\n");
      out.write("            }\n");
      out.write("            .avatarBtn:hover {\n");
      out.write("                background: blue;\n");
      out.write("                color:white;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            .QUI_btn {\n");
      out.write("                font-family: cursive;\n");
      out.write("                text-decoration: none;\n");
      out.write("                padding: 5px 20px;\n");
      out.write("                background-color: blue;\n");
      out.write("                color: white;\n");
      out.write("                width: 50px;\n");
      out.write("                display: inline-block;\n");
      out.write("            }\n");
      out.write("            .QUI_btn:hover {\n");
      out.write("                background-color: green;\n");
      out.write("                color: white;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            #QUI_Msgbox_Form {\n");
      out.write("                background: url('images/QUI/form.png');\n");
      out.write("                text-align: center;\n");
      out.write("                width: 300px;\n");
      out.write("                height: 340px;\n");
      out.write("                margin: calc(50vh - 170px) auto;\n");
      out.write("                position: relative;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            #QUI_Msgbox_Form_Caption {\n");
      out.write("                display: block;\n");
      out.write("                font-family: cursive;\n");
      out.write("                font-size: 25px;\n");
      out.write("                padding-top: 10px;\n");
      out.write("            }            \n");
      out.write("            \n");
      out.write("            #info_avatar {\n");
      out.write("                display: block; \n");
      out.write("                margin: 0px auto;\n");
      out.write("                border-radius: 20px 20px 20px 20px; \n");
      out.write("                border: 2px solid black;\n");
      out.write("                margin-top: 10px\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div id=\"QUI_Msgbox\" style=\"width: 100%;height: 100%\">\n");
      out.write("            <div id=\"QUI_GlassPane\" style=\"width: 100%;height: 100%;background-color: gray;opacity: 0.5;position: absolute;top:0px;left:0px\">\n");
      out.write("            </div>\n");
      out.write("            <div id=\"QUI_Msgbox_Form\" >  \n");
      out.write("                <!--<img src=\"images/QUI/msg.png\" style=\"position: absolute; left: 0px; top: 0px; width: 400px; height: 400px\">-->\n");
      out.write("                <b id=\"QUI_Msgbox_Form_Caption\">Thông tin tài khoản</b>\n");
      out.write("                <img id=\"info_avatar\" src=\"\" width=\"100px\">\n");
      out.write("\n");
      out.write("                <form id=\"upload-form\" onsubmit=\"return false;\"  method=\"post\" action=\"UploadServlet\" enctype=\"multipart/form-data\">\n");
      out.write("                    <label id=\"myAvatarLB\" for=\"myAvatar\" >\n");
      out.write("                        <b > &#8679;</b>\n");
      out.write("                    </label>\n");
      out.write("                    <input id=\"myAvatar\" type=\"file\" name=\"file\" size=\"1024\"  multiple accept='image/jpeg' style=\"display: none\"/>\n");
      out.write("                    <input class=\"avatarBtn\" id=\"btnUpload\" type=\"submit\" value=\"Thay đổi\" onclick=\"Change();\" hidden/>\n");
      out.write("                    <input class=\"avatarBtn\" id=\"btnCancel\" type=\"submit\" value=\"Hủy bỏ\" onclick=\"Hide()\" hidden/>\n");
      out.write("                </form>\n");
      out.write("\n");
      out.write("\n");
      out.write("                <b id=\"info_playerName\" style=\"font-family: cursive;font-size: 20px;margin-top: 15px;color:green\">lnq1996</b><br>\n");
      out.write("                <div style=\"text-align: left; font-family: cursive;font-size: 15px;margin: 20px auto\">\n");
      out.write("                    <table style=\"margin: 0px auto\">\n");
      out.write("\n");
      out.write("                        <tr><td style=\"width: 150px\">Điểm kinh nghiệm:</td><td style=\"text-align: right\"></td></tr>\n");
      out.write("                        <tr><td>Thắng/Thua: </td><td style=\"text-align: right\"></td></tr>\n");
      out.write("                    </table>\n");
      out.write("                </div>\n");
      out.write("                <div>\n");
      out.write("                    <a class=\"QUI_btn\" id=\"QUI_Msgbox_Form_Ok\" onclick=\"QUI_Form_close();\">Đóng</a>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        <script>\n");
      out.write("            function Hide()\n");
      out.write("            {\n");
      out.write("                document.getElementById('btnCancel').style.display = \"none\";\n");
      out.write("                document.getElementById('btnUpload').style.display = \"none\";\n");
      out.write("            }\n");
      out.write("            $('#myAvatar').bind('change', function () {\n");
      out.write("                var sizefile = parseInt(this.files[0].size);\n");
      out.write("                var maxsize = 1024 * 1024;\n");
      out.write("                if (sizefile > maxsize) {\n");
      out.write("                    alert('Dung lượng ảnh không vượt quá 1MB!');\n");
      out.write("                    $('#myAvatar').val('');\n");
      out.write("                } else {\n");
      out.write("                    document.getElementById('btnCancel').style.display = \"inline-block\";\n");
      out.write("                    document.getElementById('btnUpload').style.display = \"inline-block\";\n");
      out.write("                    $(\"#info_avatar\").attr(\"src\", \"avatars/lnq1996.jpg\");\n");
      out.write("                }\n");
      out.write("            });\n");
      out.write("            function Change() {\n");
      out.write("                if (document.getElementById(\"myAvatar\").files.length === 0) {\n");
      out.write("                    alert(\"Bạn chưa chọn ảnh\");\n");
      out.write("                } else {\n");
      out.write("                    $('#upload-form').attr(\"onsubmit\", \"return true;\");\n");
      out.write("                    $('#upload-form').submit();\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function QUI_Form_close() {\n");
      out.write("                $(\"#QUI_Msgbox\").fadeOut(200, function() {\n");
      out.write("                  $(\"#QUI_Msgbox\").remove();\n");
      out.write("            });\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("\n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
