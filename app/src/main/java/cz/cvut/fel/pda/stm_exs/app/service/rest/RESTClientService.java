package cz.cvut.fel.pda.stm_exs.app.service.rest;

import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;
import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


/**
 * Interface for REST Client, implementation is provided by AndroidAnnotations
 * framework
 *
 * @author jan.lantora.
 */
@Rest(rootUrl = "http://private-650dd-expsam.apiary-mock.com/samplings/", converters = {MappingJackson2HttpMessageConverter.class})
public interface RESTClientService extends RestClientErrorHandling {
    /**
     * Retrieves data for Sampling from server.
     *
     * @param samplingId id of sampling where data list is stored
     * @return Sampling which contains questions and answers
     */
    @Get("{theme}/{samplingId}")
    @Accept(MediaType.APPLICATION_JSON)
    Sampling getSampling(String theme, String samplingId);

}
