package cz.cvut.fel.pda.stm_exs.app.service.rest;

import android.util.Log;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/*
 *Handles and logs exceptions from REST Client service
 *
 * Copyright (c) 2014 Aspectworks, spol. s.r.o.
 * @author: jan.lantora
 * @version $Revision$
 */
@EBean
public class RESTClientErrorHandler implements RestErrorHandler {

    /**
     * Log exception from REST client service
     *
     * @param e Exception from REST client service
     */
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Log.e("Imp", "REST Client exception: ", e);
    }

}
