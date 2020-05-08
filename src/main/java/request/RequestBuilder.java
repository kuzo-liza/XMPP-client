package request;

import java.nio.ByteBuffer;
import javax.xml.bind.DatatypeConverter;

public class RequestBuilder {

    private static int id;

    private static final String CONNECTION_MESSAGE =
            "<?xml version='1.0'?>" +
            "<stream:stream " +
            "to='%s' " +
            "from='%s' " +
            "version='1.0' " +
            "xml:lang='en' " +
            "xmlns='jabber:client' " +
            "xmlns:stream='http://etherx.jabber.org/streams' >";

    private static final String GET_ROSTER_REQUEST =
            "<iq " +
            "from='%s' " +
            "id='%s' " +
            "type='get'> " +
            "<query xmlns='jabber:iq:roster'/> " +
            "</iq>";

    private static final String AUTH_REQUEST =
            "<auth " +
            "xmlns='urn:ietf:params:xml:ns:xmpp-sasl' " +
            "mechanism='PLAIN'>" +
            "%s" +
            "</auth>";

    private static final String BIND_RES_QUERY =
            "<iq " +
            "id='%s' " +
            "type='set'>\n" +
            "<bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'>\n" +
            "<resource>%s</resource>\n" +
            "</bind>\n" +
            "</iq>";

    private static final String SET_STATUS_QUERY =
            "<presence " +
            "from='%s'" +
            " xml:lang='en'>\n" +
            "  <show>%s</show>\n" +
            "  <status>%s</status>\n" +
            "</presence>";

    private static final String MESSAGE_QUERY =
            "<message " +
            "from='%s'\n" +
            "id='%s'\n" +
            "to='%s'\n" +
            "type='chat'\n" +
            "xml:lang='en'>\n" +
            "<body>%s</body>\n" +
            "</message>";

    private static final String ADD_CONTACT_QUERY =
            "<iq id='%s' type='%s'> " +
                    "<query xmlns='jabber:iq:roster'> " +
                       "<item jid='%s' name='%s' subscription='%s'>" +
                           "<group>%s</group>\n" +
                       "</item> " +
                    "</query> " +
            "</iq>";

    private static final String REMOVE_CONTACT_QUERY =
            "<iq id='%s' type='%s'> " +
                    "<query xmlns='jabber:iq:roster'> " +
                       "<item jid='%s' name='%s' subscription='%s'/>" +
                    "</query> " +
            "</iq>";

    private static final String CLOSE_STREAM =
            "</stream:stream>";

    private static final String ACCEPT_SUB =
            "<presence  to='%s' type='subscribed' />";

    private static final String REJECT_SUB =
            "<presence  to='%s' type='unsubscribed' />";

    private static final String SUBSCRIBE =
            "<presence from='%s' to='%s' type='subscribe' />";

    private static final String UNSUBSCRIBE =
            "<presence from='%s' to='%s' type='unsubscribe' />";

    private static final String RESULT =
            "<iq id='%s' type='result' />";

    public static ByteBuffer createResultRequest(String id) {
        return ByteBuffer.wrap(String.format(RESULT, id).getBytes());
    }

    public static ByteBuffer createCloseStreamRequest() {
        return ByteBuffer.wrap(CLOSE_STREAM.getBytes());
    }

    public static ByteBuffer createAcceptSubRequest(String contactJID) {
        return ByteBuffer.wrap(
                String.format(ACCEPT_SUB, contactJID).getBytes()
        );
    }

    public static ByteBuffer createRejectSubRequest(String contactJID) {
        return ByteBuffer.wrap(
                String.format(REJECT_SUB, contactJID).getBytes()
        );
    }

    public static ByteBuffer createSubscribeRequest(String userJID, String contactJID) {
        return ByteBuffer.wrap(
                String.format(SUBSCRIBE, userJID, contactJID).getBytes()
        );
    }

    public static ByteBuffer createUnsubscribeSubRequest(String userJID, String contactJID) {
        return ByteBuffer.wrap(
                String.format(UNSUBSCRIBE, userJID, contactJID).getBytes()
        );
    }

    public static ByteBuffer createRemoveContactRequest(String contactJID, String contactName) {
        return ByteBuffer.wrap(
                String.format(REMOVE_CONTACT_QUERY, id++, "set", contactJID, contactName, "remove").getBytes()
        );
    }

    public static ByteBuffer createAddContactRequest(String contactJID, String contactName, String sub, String group) {
        return ByteBuffer.wrap(
                String.format(ADD_CONTACT_QUERY, id++, "set", contactJID, contactName, sub, group).getBytes()
        );
    }

    public static ByteBuffer createMessageRequest(String userJID, String anotherUserJID, String message) {
        return ByteBuffer.wrap(
                String.format(MESSAGE_QUERY, userJID, id++, anotherUserJID, message).getBytes()
        );
    }

//    public static ByteBuffer createSetStatusRequest(String userJID, String statusType) {
//        return createSetStatusRequest(userJID, statusType, "");
//    }

    public static ByteBuffer createSetStatusRequest( String userJID, String statusType, String statusText) {
        return ByteBuffer.wrap(
                String.format(SET_STATUS_QUERY, userJID, statusType, statusText).getBytes()
        );
    }

    public static ByteBuffer createBindRequest(String resource) {
        return ByteBuffer.wrap(
                String.format(BIND_RES_QUERY, id++, resource).getBytes()
        );
    }

    public static ByteBuffer createAuthRequest(String userJID, String password) {
        byte[] data = ("\u0000" + userJID + "\u0000" + password).getBytes();
        String base64Encoded = DatatypeConverter.printBase64Binary(data);
        return ByteBuffer.wrap(
                String.format(AUTH_REQUEST, base64Encoded).getBytes()
        );
    }

    public static ByteBuffer createConnectionRequest(String hostJID, String clientJID) {
        return ByteBuffer.wrap(
                String.format(CONNECTION_MESSAGE, hostJID, clientJID).getBytes()
        );
    }

    public static ByteBuffer createGetRosterRequest(String clientJID) {
        return ByteBuffer.wrap(
                String.format(GET_ROSTER_REQUEST,clientJID, id++).getBytes()
        );
    }
}
