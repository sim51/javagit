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
package com.logisima.javagit.cli.init;

import com.logisima.javagit.cli.ICommandResponse;

public class GitInitResponse implements ICommandResponse {

    public boolean initialized   = false;
    public boolean reinitialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isReinitialized() {
        return reinitialized;
    }

    public void setReinitialized(boolean reinitialized) {
        this.reinitialized = reinitialized;
    }

    public boolean containsError() {
        // TODO Auto-generated method stub
        return false;
    }
}