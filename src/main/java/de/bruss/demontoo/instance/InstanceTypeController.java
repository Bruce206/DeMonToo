package de.bruss.demontoo.instance;

import de.bruss.demontoo.server.Server;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/instanceType")
public class InstanceTypeController {

    @Autowired
    private InstanceTypeService instancetypeService;

    @Autowired
    private SimpMessagingTemplate template;

    private final Logger logger = LoggerFactory.getLogger(InstanceTypeController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<InstanceType> findAllInstanceTypes() {
        return instancetypeService.findAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public InstanceType getInstanceType(@PathVariable String name) {
        return instancetypeService.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteInstanceType(@PathVariable long id) {
        instancetypeService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public InstanceType updateInstanceType(@PathVariable long id, @RequestBody InstanceType instanceType) {
        return instancetypeService.save(instanceType);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public InstanceType saveInstanceType(@RequestBody InstanceType instanceType) {
        return instancetypeService.save(instanceType);
    }

    @RequestMapping(value="/image/{id}", method = RequestMethod.POST)
    public void setImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        instancetypeService.setFile(id, file.getBytes());
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public void setUpdate(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        instancetypeService.setUpdateFile(id, file);
    }

    @RequestMapping(value="/install/{id}", method = RequestMethod.POST)
    public void setUpdate(@PathVariable Long instanceTypeId, @RequestBody InstanceInstallationRequest request) throws IOException {
        instancetypeService.installNewInstance(instanceTypeId, request);
    }

    @Getter
    @Setter
    public static class InstanceInstallationRequest {
        // deployment-settings
        private Server server;
        private String ip;

        private String domain;
        private String customer;
        private String identifier;

        // application-properties
        private String port;
        private List<ApplicationProperty> additionalApplicationProperties;
        private String activeSpringProfiles;

        @Getter
        @Setter
        public class ApplicationProperty {
            private String key;
            private String value;
        }
    }
}
