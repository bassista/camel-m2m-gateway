package camelm2m.shell.endpoints

import camelm2m.shell.dto.PostBody
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import zed.deployer.manager.DeployablesManager

class DeployMvcEndpoint extends EndpointMvcAdapter {

    private final DeployablesManager deployablesManager

    DeployMvcEndpoint(DeployEndpoint delegate, DeployablesManager deployablesManager) {
        super(delegate)
        this.deployablesManager = deployablesManager
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deploy(@RequestBody PostBody post) {
        try {
            deployablesManager.deploy(post.uri)
            new ResponseEntity(HttpStatus.OK)
        } catch (Exception ex) {
            new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
