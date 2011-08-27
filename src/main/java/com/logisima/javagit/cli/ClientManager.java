/**
 *  This file is part of LogiSima (http://www.logisima.com).
 *
 *  JavGit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavGit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with LogiSima-Common.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the <code>IClient</code> instances of the various <code>ClientType</code>s. It contains factory
 * type functionality (the <code>getClientType()</code> method) but manages much more than just creating
 * <code>IClient</code> instances.
 */
public class ClientManager {

    // An enumeration of the available client types.
    public static enum ClientType {
        CLI
    };

    // The singleton instance of the class <code>ClientManager</code>.
    private static ClientManager INSTANCE = new ClientManager();

    /**
     * Gets the singleton instance.
     * 
     * @return The singleton instance.
     */
    public static ClientManager getInstance() {
        return INSTANCE;
    }

    // The preferred client type.
    private ClientType               preferredClientType = ClientType.CLI;

    /**
     * A <code>Map</code> to hold <code>IClient</code> instances for the ClientTypes by <code>ClientType</code>.
     */
    private Map<ClientType, IClient> clientImpls         = new HashMap<ClientType, IClient>();

    /**
     * Private to make this class a singleton.
     */
    private ClientManager() {
    }

    /**
     * Gets an instance of the specified client type.
     * 
     * @param clientType The type of client to get.
     * @return An instance of the specified client type.
     */
    public IClient getClientInstance(ClientType clientType) {
        IClient clientInstance = clientImpls.get(clientType);
        if (null == clientInstance) {
            if (ClientType.CLI == clientType) {
                clientInstance = new Client();
            }
            if (null != clientInstance) {
                clientImpls.put(clientType, clientInstance);
            }
        }
        return clientInstance;
    }

    /**
     * Gets an instance of the preferred client type as set via the <code>setPreferredClientType()</code> method.
     * 
     * @return An IClient instance for the preferred client type.
     */
    public IClient getPreferredClient() {
        return getClientInstance(preferredClientType);
    }

    /**
     * Sets the preferred client type.
     * 
     * @param preferredClientType The type to set as the preferred client type. If the value is null, then the preferred
     *        client type is set to <code>ClientType.CLI</code>.
     */
    public void setPreferredClientType(ClientType preferredClientType) {
        if (null == preferredClientType) {
            this.preferredClientType = ClientType.CLI;
        }
        else {
            this.preferredClientType = preferredClientType;
        }
    }
}
