package finance

import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import static groovyx.net.http.ContentType.*

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification;

import spock.lang.Shared


class SparklrSpec extends Specification {

    @Shared
    def username = 'marissa'
    @Shared
    def password = 'koala'
    @Shared
    RESTClient client

    def setupSpec() {
        client = new RESTClient('http://localhost:8080/sparklr2/')
        client.setContentType JSON
//        client.client.addRequestInterceptor(new HttpRequestInterceptor() {
//            void process(HttpRequest httpRequest, HttpContext httpContext) {
//                httpRequest.addHeader('Authorization', 'Basic ' + "$username:$password".toString().bytes.encodeBase64().toString())
//            }
//        })
    }

    def 'direct reports'() {
        when:
        def resp = client.post(path: 'oauth/token', body : [username: username, password: password], requestContentType : URLENC)
        then:
        1 == 1
        println resp.data
    }
}
