package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 11/30/2016.
 */

@XmlRootElement(name = "auth-response")
public class AuthorizedResponse {

    private String id;
    private String authorizedSession;
    private String nextUrl;

    public AuthorizedResponse() {  }

    public AuthorizedResponse(String id, String authorizedSession, String nextUrl) {
        this.id = id;
        this.authorizedSession = authorizedSession;
        this.nextUrl = nextUrl;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getAuthorizedSession() {
        return authorizedSession;
    }

    public void setAuthorizedSession(String authorizedSession) {
        this.authorizedSession = authorizedSession;
    }

    @XmlElement
    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }



    @Override
    public String toString() {
        return "AuthorizedResponse : {" +
                "id : " + id +
                ", authorizedSession : " + authorizedSession +
                ", nextUrl : " + nextUrl +
                " }";
    }
}
