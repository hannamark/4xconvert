/**
* Copyright (c) 2009, 5AM Solutions, Inc.
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* - Redistributions of source code must retain the above copyright notice,
*  this list of conditions and the following disclaimer.
* 
* - Redistributions in binary form must reproduce the above copyright notice,
*  this list of conditions and the following disclaimer in the documentation
*  and/or other materials provided with the distribution.
* 
* - Neither the name of the author nor the names of its contributors may be
*  used to endorse or promote products derived from this software without
*  specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package gov.nih.nci.accrual.outweb.dto.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


/**
 * @author hniedner
 *
 */
public class PersonNameConverterTest {

    //setup
    private static final String FFIRST = "Primus";
    private static final String FIRST = "John";
    private static final String LAST = "Doe";
    private static final String BOGUS = "FOOO";
    

    /**
     * Test method for {@link gov.nih.nci.accrual.outweb.dto.util.PersonNameConverter#convert(java.util.List, gov.nih.nci.accrual.outweb.dto.util.PersonName)}.
     */
    @Test
    public void testConvert() {
        // constructor used in UserAccountAction
        final PersonName dest = new PersonNameImpl("", null, "");
        final List<Enxp> src = getEnxpList();
        
        //test
        final PersonNameConverter converter = new PersonNameConverter();
        converter.convert(src, dest);
        
        assertSame(LAST, dest.getLastName());
        assertNull(dest.getMiddleName());
        assertSame(FIRST, dest.getFirstName());
    }
    
    /**
     * Test method for {@link gov.nih.nci.accrual.outweb.dto.util.PersonNameConverter#convert(java.util.List, gov.nih.nci.accrual.outweb.dto.util.PersonName)}.
     */
    @Test
    public void testConvertNoBlankFirstName() {
        final PersonName dest = new PersonNameImpl();
        //first & last name are not BLANK
        dest.setFirstName(FFIRST);
        dest.setLastName(FFIRST);
        final List<Enxp> src = getEnxpList();
        
        //test
        final PersonNameConverter converter = new PersonNameConverter();
        converter.convert(src, dest);

        assertSame(FFIRST, dest.getFirstName());
        assertSame(LAST, dest.getLastName());
        assertSame(FIRST, dest.getMiddleName());  
    }
    
    private List<Enxp> getEnxpList() {
        final List<Enxp> src = new ArrayList<Enxp>();
        //first name
        final Enxp first = new Enxp();
        first.setValue(FIRST);
        first.setType(EntityNamePartType.GIV);
        src.add(first);
        //last name
        final Enxp last = new Enxp();
        last.setValue(LAST);
        last.setType(EntityNamePartType.FAM);
        src.add(last);
        //other name parts that should NOT show up in the converted name
        final Enxp del = new Enxp();
        del.setValue(BOGUS);
        del.setType(EntityNamePartType.DEL);
        src.add(del);
        final Enxp sfx = new Enxp();
        sfx.setValue(BOGUS);
        sfx.setType(EntityNamePartType.SFX);
        src.add(sfx);
        final Enxp pfx = new Enxp();
        pfx.setValue(BOGUS);
        pfx.setType(EntityNamePartType.PFX);
        src.add(pfx);
        
        return src;
    }

}
