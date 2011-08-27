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

public class GitInitOptions {

    private boolean optBare             = false;
    private boolean optTemplate         = false;
    private String  optTemplateDirecory = null;
    private boolean optSharedUmask      = false;
    private boolean optSharedGroup      = false;
    private String  optSharedGroupName  = null;
    private boolean optSharedAll        = false;
    private boolean optSharedOctal      = false;
    private int     optSharedOctalValue = 0;

    /**
     * @return the optBare
     */
    public boolean isOptBare() {
        return optBare;
    }

    /**
     * @param optBare the optBare to set
     */
    public void setOptBare(boolean optBare) {
        this.optBare = optBare;
    }

    /**
     * @return the optTemplate
     */
    public boolean isOptTemplate() {
        return optTemplate;
    }

    /**
     * @param optTemplate the optTemplate to set
     */
    public void setOptTemplate(boolean optTemplate) {
        this.optTemplate = optTemplate;
    }

    /**
     * @return the optTemplateDirecory
     */
    public String getOptTemplateDirecory() {
        return optTemplateDirecory;
    }

    /**
     * @param optTemplateDirecory the optTemplateDirecory to set
     */
    public void setOptTemplateDirecory(String optTemplateDirecory) {
        this.optTemplateDirecory = optTemplateDirecory;
    }

    /**
     * @return the optSharedUmask
     */
    public boolean isOptSharedUmask() {
        return optSharedUmask;
    }

    /**
     * @param optSharedUmask the optSharedUmask to set
     */
    public void setOptSharedUmask(boolean optSharedUmask) {
        this.optSharedUmask = optSharedUmask;
    }

    /**
     * @return the optSharedGroup
     */
    public boolean isOptSharedGroup() {
        return optSharedGroup;
    }

    /**
     * @param optSharedGroup the optSharedGroup to set
     */
    public void setOptSharedGroup(boolean optSharedGroup) {
        this.optSharedGroup = optSharedGroup;
    }

    /**
     * @return the optSharedGroupName
     */
    public String getOptSharedGroupName() {
        return optSharedGroupName;
    }

    /**
     * @param optSharedGroupName the optSharedGroupName to set
     */
    public void setOptSharedGroupName(String optSharedGroupName) {
        this.optSharedGroupName = optSharedGroupName;
    }

    /**
     * @return the optSharedAll
     */
    public boolean isOptSharedAll() {
        return optSharedAll;
    }

    /**
     * @param optSharedAll the optSharedAll to set
     */
    public void setOptSharedAll(boolean optSharedAll) {
        this.optSharedAll = optSharedAll;
    }

    /**
     * @return the optSharedOctal
     */
    public boolean isOptSharedOctal() {
        return optSharedOctal;
    }

    /**
     * @param optSharedOctal the optSharedOctal to set
     */
    public void setOptSharedOctal(boolean optSharedOctal) {
        this.optSharedOctal = optSharedOctal;
    }

    /**
     * @return the optSharedOctalValue
     */
    public int getOptSharedOctalValue() {
        return optSharedOctalValue;
    }

    /**
     * @param optSharedOctalValue the optSharedOctalValue to set
     */
    public void setOptSharedOctalValue(int optSharedOctalValue) {
        this.optSharedOctalValue = optSharedOctalValue;
    }

}
