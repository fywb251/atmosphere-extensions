/*
 * Copyright 2013 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.gwt20.managed;

import org.atmosphere.cpr.Action;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereInterceptorAdapter;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.gwt20.client.AtmosphereMessage;
import org.atmosphere.gwt20.shared.Constants;

/**
 * De-serialize GWT object into a String so we can use {@link org.atmosphere.cpr.Broadcaster} and non GWT application as client.
 * All AtmosphereInterceptor will work out of the box without requiring any change.
 *
 * This interceptor is normally used with the {@link org.atmosphere.config.service.ManagedService} annotation
 * and must always be used with {@link org.atmosphere.gwt20.server.GwtRpcInterceptor}.
 *
 * @author Jeanfrancois Arcand
 */
public class RPCEventDeserializerInterceptor extends AtmosphereInterceptorAdapter {

    @Override
    public void configure(AtmosphereConfig config) {
        config.framework().broadcasterFilters(new RPCEventFilter());
    }

    @Override
    public Action inspect(AtmosphereResource r) {
        Object msg = r.getRequest().getAttribute(Constants.MESSAGE_OBJECT);
        if (msg != null && AtmosphereMessage.class.isAssignableFrom(msg.getClass())) {
            r.getRequest().body(AtmosphereMessage.class.cast(msg).getMessage());
        }
        return Action.CONTINUE;
    }
}
