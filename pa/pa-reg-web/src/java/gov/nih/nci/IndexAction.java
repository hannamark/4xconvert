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

import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * 
 */
@Conversion()
public class IndexAction extends ActionSupport {
    
    private Date now = new Date(System.currentTimeMillis());
    
    /**
     * @return now
     */
    @TypeConversion(converter = "com.fdar.apress.s2.DateConverter")
    public Date getDateNow() { return now; }
    
    /**
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     * @return action result
     */
    public String execute() {
        now = new Date(System.currentTimeMillis());
        return SUCCESS;
    }
}
