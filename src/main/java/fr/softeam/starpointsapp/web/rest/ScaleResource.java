package fr.softeam.starpointsapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.softeam.starpointsapp.domain.Scale;
import fr.softeam.starpointsapp.repository.ScaleRepository;
import fr.softeam.starpointsapp.security.AuthoritiesConstants;
import fr.softeam.starpointsapp.web.rest.util.HeaderUtil;
import fr.softeam.starpointsapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Scale.
 */
@RestController
@RequestMapping("/api")
public class ScaleResource {

    private final Logger log = LoggerFactory.getLogger(ScaleResource.class);

    @Inject
    private ScaleRepository scaleRepository;

    /**
     * POST  /scales : Create a new scale.
     *
     * @param scale the scale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scale, or with status 400 (Bad Request) if the scale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scales",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Scale> createScale(@RequestBody Scale scale) throws URISyntaxException {
        log.debug("REST request to save Scale : {}", scale);
        if (scale.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("scale", "idexists", "A new scale cannot already have an ID")).body(null);
        }
        Scale result = scaleRepository.save(scale);
        return ResponseEntity.created(new URI("/api/scales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("scale", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scales : Updates an existing scale.
     *
     * @param scale the scale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scale,
     * or with status 400 (Bad Request) if the scale is not valid,
     * or with status 500 (Internal Server Error) if the scale couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scales",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Scale> updateScale(@RequestBody Scale scale) throws URISyntaxException {
        log.debug("REST request to update Scale : {}", scale);
        if (scale.getId() == null) {
            return createScale(scale);
        }
        Scale result = scaleRepository.save(scale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("scale", scale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scales : get all the scales.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scales in body
     */
    @RequestMapping(value = "/scales",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> getAllScales(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all Scales");
        Page page = scaleRepository.findAllScales(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /scales/:id : get the "id" scale.
     *
     * @param id the id of the scale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scale, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/scales/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Scale> getScale(@PathVariable Long id) {
        log.debug("REST request to get Scale : {}", id);
        Scale scale = scaleRepository.findOne(id);
        return Optional.ofNullable(scale)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scales/:id : delete the "id" scale.
     *
     * @param id the id of the scale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/scales/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteScale(@PathVariable Long id) {
        log.debug("REST request to delete Scale : {}", id);
        scaleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("scale", id.toString())).build();
    }

}
