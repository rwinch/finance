package finance

import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import static groovyx.net.http.ContentType.*

import groovy.json.JsonOutput;
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification;

import spock.lang.Shared


class ExpensesSpec extends Specification {

    @Shared
    def username = 'sally'
    @Shared
    def password = 'password'
    @Shared
    RESTClient client

    def setupSpec() {
        client = new RESTClient('http://localhost:8080/finance/springsource.com/')
        client.setContentType JSON
        client.client.addRequestInterceptor(new HttpRequestInterceptor() {
            void process(HttpRequest httpRequest, HttpContext httpContext) {
                httpRequest.addHeader('Authorization', 'Basic ' + "springsource-finance:password".toString().bytes.encodeBase64().toString())
            }
        })
    }

    def 'direct reports'() {
        setup:
        def resp = client.post(path: 'oauth/token', body : [username: 'spring', password: 'password', grant_type : 'password', scope:'supervisor'], requestContentType : URLENC)
        def data = resp.data
        when:
        resp = client.get(path: "expenses/direct-reports",query:["access_token": data.access_token])
        data = resp.data
        println JsonOutput.prettyPrint(data.toString())
        then:
        data.size() == 3
    }
}
