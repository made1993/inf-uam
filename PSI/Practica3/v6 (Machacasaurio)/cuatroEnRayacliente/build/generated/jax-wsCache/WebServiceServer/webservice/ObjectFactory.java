
package webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LoginResponse_QNAME = new QName("http://webservice/", "loginResponse");
    private final static QName _Exception_QNAME = new QName("http://webservice/", "Exception");
    private final static QName _MakeMoveResponse_QNAME = new QName("http://webservice/", "makeMoveResponse");
    private final static QName _CreateUserResponse_QNAME = new QName("http://webservice/", "createUserResponse");
    private final static QName _HasWonResponse_QNAME = new QName("http://webservice/", "hasWonResponse");
    private final static QName _CreateGameResponse_QNAME = new QName("http://webservice/", "createGameResponse");
    private final static QName _Show_QNAME = new QName("http://webservice/", "show");
    private final static QName _MakeComputerMoveResponse_QNAME = new QName("http://webservice/", "makeComputerMoveResponse");
    private final static QName _ShowResponse_QNAME = new QName("http://webservice/", "showResponse");
    private final static QName _CreateGame_QNAME = new QName("http://webservice/", "createGame");
    private final static QName _MakeMove_QNAME = new QName("http://webservice/", "makeMove");
    private final static QName _Logout_QNAME = new QName("http://webservice/", "logout");
    private final static QName _MakeComputerMove_QNAME = new QName("http://webservice/", "makeComputerMove");
    private final static QName _CreateUser_QNAME = new QName("http://webservice/", "createUser");
    private final static QName _HasWon_QNAME = new QName("http://webservice/", "hasWon");
    private final static QName _Login_QNAME = new QName("http://webservice/", "login");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link HasWonResponse }
     * 
     */
    public HasWonResponse createHasWonResponse() {
        return new HasWonResponse();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link Show }
     * 
     */
    public Show createShow() {
        return new Show();
    }

    /**
     * Create an instance of {@link CreateGameResponse }
     * 
     */
    public CreateGameResponse createCreateGameResponse() {
        return new CreateGameResponse();
    }

    /**
     * Create an instance of {@link MakeMoveResponse }
     * 
     */
    public MakeMoveResponse createMakeMoveResponse() {
        return new MakeMoveResponse();
    }

    /**
     * Create an instance of {@link MakeMove }
     * 
     */
    public MakeMove createMakeMove() {
        return new MakeMove();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link MakeComputerMove }
     * 
     */
    public MakeComputerMove createMakeComputerMove() {
        return new MakeComputerMove();
    }

    /**
     * Create an instance of {@link ShowResponse }
     * 
     */
    public ShowResponse createShowResponse() {
        return new ShowResponse();
    }

    /**
     * Create an instance of {@link MakeComputerMoveResponse }
     * 
     */
    public MakeComputerMoveResponse createMakeComputerMoveResponse() {
        return new MakeComputerMoveResponse();
    }

    /**
     * Create an instance of {@link CreateGame }
     * 
     */
    public CreateGame createCreateGame() {
        return new CreateGame();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link HasWon }
     * 
     */
    public HasWon createHasWon() {
        return new HasWon();
    }

    /**
     * Create an instance of {@link ReturnError }
     * 
     */
    public ReturnError createReturnError() {
        return new ReturnError();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeMoveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "makeMoveResponse")
    public JAXBElement<MakeMoveResponse> createMakeMoveResponse(MakeMoveResponse value) {
        return new JAXBElement<MakeMoveResponse>(_MakeMoveResponse_QNAME, MakeMoveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasWonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "hasWonResponse")
    public JAXBElement<HasWonResponse> createHasWonResponse(HasWonResponse value) {
        return new JAXBElement<HasWonResponse>(_HasWonResponse_QNAME, HasWonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "createGameResponse")
    public JAXBElement<CreateGameResponse> createCreateGameResponse(CreateGameResponse value) {
        return new JAXBElement<CreateGameResponse>(_CreateGameResponse_QNAME, CreateGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Show }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "show")
    public JAXBElement<Show> createShow(Show value) {
        return new JAXBElement<Show>(_Show_QNAME, Show.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeComputerMoveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "makeComputerMoveResponse")
    public JAXBElement<MakeComputerMoveResponse> createMakeComputerMoveResponse(MakeComputerMoveResponse value) {
        return new JAXBElement<MakeComputerMoveResponse>(_MakeComputerMoveResponse_QNAME, MakeComputerMoveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "showResponse")
    public JAXBElement<ShowResponse> createShowResponse(ShowResponse value) {
        return new JAXBElement<ShowResponse>(_ShowResponse_QNAME, ShowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "createGame")
    public JAXBElement<CreateGame> createCreateGame(CreateGame value) {
        return new JAXBElement<CreateGame>(_CreateGame_QNAME, CreateGame.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeMove }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "makeMove")
    public JAXBElement<MakeMove> createMakeMove(MakeMove value) {
        return new JAXBElement<MakeMove>(_MakeMove_QNAME, MakeMove.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Logout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "logout")
    public JAXBElement<Logout> createLogout(Logout value) {
        return new JAXBElement<Logout>(_Logout_QNAME, Logout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeComputerMove }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "makeComputerMove")
    public JAXBElement<MakeComputerMove> createMakeComputerMove(MakeComputerMove value) {
        return new JAXBElement<MakeComputerMove>(_MakeComputerMove_QNAME, MakeComputerMove.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasWon }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "hasWon")
    public JAXBElement<HasWon> createHasWon(HasWon value) {
        return new JAXBElement<HasWon>(_HasWon_QNAME, HasWon.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

}
