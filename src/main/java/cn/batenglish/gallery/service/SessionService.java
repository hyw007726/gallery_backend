package cn.batenglish.gallery.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SessionService {
    @Autowired
    private final HttpSession session;

    public SessionService(HttpSession session) {
        this.session = session;
    }
    public void setCode(String code){
        session.setAttribute("code",code);
    }
    public String getCode(){
        Object code = session.getAttribute("code");
        return code != null ? code.toString() : "Invalid";
//        return session.getAttribute("code").toString();
    }
    public void setEmail(String email){
        session.setAttribute("email",email);
    }
    public String getEmail(){
        return (String)session.getAttribute("email");
    }

    public boolean validateSession(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        String storedSessionId = session.getId();

        if (!sessionId.equals(storedSessionId)) {
            return false;
        }

        return true;
    }
}
