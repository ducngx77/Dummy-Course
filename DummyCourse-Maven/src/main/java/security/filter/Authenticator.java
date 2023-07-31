package security.filter;

import controller.course.UpdateCourse;
import entity.user.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ADMIN
 */
public class Authenticator implements Filter {

    private static final boolean debug = true;
    private final String[] studentPages = {"login", "homeStudent.jsp", "error.jsp", "index.jsp", "home", "course-details", "student.jsp", "student-profile.jsp", "student-quiz.jsp", "student-quiz-result.jsp", "student-quiz-history.jsp", "student-quiz-history-detail.jsp", "course-reviews", "feedback", "systemFeedback.jsp", "LecturerProfile.jsp"};
    private final String[] lecturePages = {"AddQuestion-actions", "addquestion", "course-details", "login", "homeLecture.jsp", "home", "error.jsp", "lecture", "lecture.jsp", "lecture-profile.jsp", "lecture-quiz.jsp", "lecture-quiz-history.jsp", "lecture-quiz-history-detail.jsp", "course-reviews", "feedback", "systemFeedback.jsp", "LecturerProfile.jsp"};
    private final String[] adminPages = {"login", "homeAdmin.jsp", "home", "error.jsp", "admin", "admin.jsp", "admin-profile.jsp", "admin-quiz.jsp", "admin-quiz-history.jsp", "admin-quiz-history-detail.jsp", "courseType", "updateCourse", "UpdateCourse.jsp", "LecturerProfile.jsp"};
    private final String[] guestPages = {"course-details", "course-details-view.jsp", "home.jsp", "home", "lectureHome.jsp", "studentHome.jsp", "adminHome.jsp", "test", "newStudentHome.jsp", "error.jsp", "studentHome.jsp", "login", "register",  "course-reviews", "LecturerProfile.jsp"};
    private final String[] resourceRequests = {"/CSS/", "/resource/"};
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public Authenticator() {
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    private boolean isResourceRequest(String page) {
        for (String resourceRequest : resourceRequests) {
            if (page.contains(resourceRequest)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStudentPage(String page) {
        for (String studentPage : studentPages) {
            if (page.contains(studentPage)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLecturePage(String page) {
        for (String lecturePage : lecturePages) {
            if (page.contains(lecturePage)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdminPage(String page) {
        for (String adminPage : adminPages) {
            if (page.contains(adminPage)) {
                return true;
            }
        }
        return false;
    }

    private boolean isGuestPage(String page) {
        for (String guestPage : guestPages) {
            if (page.contains(guestPage)) {
                return true;
            }
        }
        return false;
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleAuthentication:DoBeforeProcessing");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Role userRole = (Role) httpRequest.getSession().getAttribute("role");
        String page = httpRequest.getRequestURI();
        System.out.println(page);
/*
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Role userRole = (Role) httpRequest.getSession().getAttribute("role");
        String page = httpRequest.getRequestURI();
        System.out.println(page);
        if (isResourceRequest(page)) return;
        if (userRole == null) {
            if (!isGuestPage(page)) {
                httpRequest.getSession().setAttribute("error", "You don't have permission to access this page. Please login to access this page.");
                httpRequest.getSession().setAttribute("page", "login");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
                return;
            } else return;
        }
        String role = (userRole).getUserType();
        if (role.equals("student") && !isStudentPage(page)) {
            httpRequest.getSession().setAttribute("error", "You don't have permission to access this page. Please login with other permission account to access this page.");
            httpRequest.getSession().setAttribute("page", "login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
        } else if (role.equals("lecture") && !isLecturePage(page)) {
            httpRequest.getSession().setAttribute("error", "You don't have permission to access this page. Please login with other permission account to access this page.");
            httpRequest.getSession().setAttribute("page", "login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
        } else if (role.equals("admin") && !isAdminPage(page)) {
            httpRequest.getSession().setAttribute("error", "You don't have permission to access this page. Please login with other permission account to access this page.");
            httpRequest.getSession().setAttribute("page", "login");
            httpRequest.setAttribute("error", "You don't have permission to access this page. Please login with admin account to access this page.");
            httpRequest.setAttribute("page", "login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
        }*/

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("Authenticator:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed.
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("Authenticator:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("Authenciator:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("Authenciator()");
        }
        String sb = "Authenciator(" + filterConfig +
                ")";
        return (sb);
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}