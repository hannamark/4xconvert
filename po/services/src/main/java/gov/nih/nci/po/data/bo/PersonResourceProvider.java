/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.data.bo;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.util.ValidIi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * ResourceProvider that has a Person player and Organization scoper.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none" generate-helper-methods="false"
 *      class="gov.nih.nci.services.correlation.PersonResourceProviderDTO"
 *      implements="gov.nih.nci.services.PoDto"
 */
@Entity
public class PersonResourceProvider implements PersistentObject {

    private static final long serialVersionUID = -4866225509121969001L;
    private Long id;
    private Person player;
    private Organization scoper;
    private RoleStatus status;
    private Ii identifier;

    // TODO PO-432 - not including statusDate until jira issue is resolved one way or the other

    /**
     * {@inheritDoc}
     * @xsnapshot.property match="iso"
     *         type="gov.nih.nci.coppa.iso.Ii" name="identifier"
     *         snapshot-transformer="gov.nih.nci.po.data.convert.IdConverter$PersonResourceProviderIdConverter"
     *         model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the player.  never null.
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Ii" name="playerIdentifier"
     *            snapshot-transformer="gov.nih.nci.po.data.convert.PersistentObjectConverter$PersistentPersonConverter"
     *            model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @ManyToOne
    @NotNull
    @ForeignKey(name = "personrprole_player_fkey")
    public Person getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Person player) {
        this.player = player;
    }

    /**
     * @return the scoper. may be null.
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Ii" name="scoperIdentifier"
     *            snapshot-transformer="gov.nih.nci.po.data.convert.PersistentObjectConverter$PersistentOrgConverter"
     *            model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @ManyToOne
    @ForeignKey(name = "personrpnrole_scoper_fkey")
    public Organization getScoper() {
        return scoper;
    }

    /**
     * @param scoper the scoper to set
     */
    public void setScoper(Organization scoper) {
        this.scoper = scoper;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RoleStatus status) {
        this.status = status;
    }

    /**
     * @return the status
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Cd"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.RoleStatusConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.CdConverter"
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    public RoleStatus getStatus() {
        return this.status;
    }

    /**
     * @return the Id that <code>scoper</code> uses to identify <code>player</code>.  This is
     *         distinct from the id of <em>this role</em>, which is system assigned.
     * @xsnapshot.property match="iso" name="assignedId"
     */
    @Type(type = "gov.nih.nci.po.util.IiCompositeUserType")
    @Columns(columns = {
            @Column(name = "identifier_null_flavor"),
            @Column(name = "identifier_displayable"),
            @Column(name = "identifier_extension"),
            @Column(name = "identifier_identifier_name"),
            @Column(name = "identifier_reliability"),
            @Column(name = "identifier_root"),
            @Column(name = "identifier_scope")
    })
    @ValidIi
    public Ii getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the Id that <code>scoper</code> uses to identify <code>player</code>
     */
    public void setIdentifier(Ii identifier) {
        this.identifier = identifier;
    }
}
