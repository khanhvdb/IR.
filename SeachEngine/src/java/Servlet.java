/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Enumeration;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author KhanhVDb
 */
@WebServlet(urlPatterns = {"/Search"})
public class Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Vector ListPhim = new Vector();
            request.setCharacterEncoding("UTF-8");
            String value = request.getParameter("value");
            SearchFiles result = new SearchFiles();
            ListPhim = result.Search(value);
            String List = "";
            for(int i = 0; i < ListPhim.size(); i++){
                File file = new File((String)ListPhim.get(i));
                BufferedReader br = null;
                FileInputStream fi = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fi, "utf-8");
		br = new BufferedReader(isr);
                String sCurrentLine = "";
                String tem = "";
                char c;
                int value2;
                
                while((value2 = br.read()) != -1){
                    c = (char)value2;
                    sCurrentLine = sCurrentLine + c;
                
                }
                int a = sCurrentLine.indexOf("Title");
                String link = sCurrentLine.substring(5, a-1);
                int b = sCurrentLine.indexOf("Content");
                String content = sCurrentLine.substring(b+7);
                if (fi != null)fi.close();
                

                List = List + "<a href=\""+ link +"\">" + file.getName() +"</a>";
                List = List + "</br>";
                List = List + content;
                List = List + "</br>";
            }
            request.setAttribute("value", "khanh");
            Map<String, String[]> extraParams = new TreeMap<String, String[]>();
            extraParams.put("list", new String[]{List, "value2"});

            HttpServletRequest wrappedRequest = new PrettyFacesWrappedRequest(request, extraParams);
          
            //request.setAttribute("list", ListPhim);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(wrappedRequest, response);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            out.println(value);
            
            out.println("</br>");
            for(int i = 0; i < ListPhim.size(); i++){
                out.println(ListPhim.get(i));
                out.println("</br>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
