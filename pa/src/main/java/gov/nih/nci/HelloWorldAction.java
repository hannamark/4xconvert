/*
 * $Id: RequestUtils.java 394468 2006-04-16 12:16:03Z tmjee $
 *
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nih.nci;

import java.util.Date;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author Hugh
 *
 */
/**
 * @author Hugh
 *
 */
/**
 * @author Hugh
 *
 */
@Validation()
@Conversion()
public class HelloWorldAction extends ActionSupport {

    static final long serialVersionUID = 12341342L;
    
    private Date now;
    private String name;

    /**
     * @param nowx now
     */
    @TypeConversion(converter = "gov.nih.nci.DateConverter")
    @RequiredFieldValidator(message = "Please enter the date")
    public void setDateNow(Date nowx) { this.now = nowx; }
    
    
    /**
     * @return date
     */
    public Date getDateNow() { return now; }

    /**
     * @param name name
     */
    @RequiredStringValidator(message = "Please enter a name", trim = true)
    public void setName(String name) { this.name = name; }
    
    /**
     * @return name
     */
    public String getName() { return this.name; }

    /**
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     * @return action result
     */
    public String execute() {
        return SUCCESS;
    }
}
