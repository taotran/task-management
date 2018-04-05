package com.pycogroup.taotran.task.management.core.service.acl;

import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class AclUtilServiceBean<T extends AbstractDocument> implements AclUtilService<T> {


    private final MutableAclService aclService;

    @Autowired
    public AclUtilServiceBean(MutableAclService aclService) {

        Assert.notNull(aclService, "'aclService' must not be null!");

        this.aclService = aclService;
    }

    @Override
    @Transactional
    public void setOwnerRightForPersistenceObject(T t) {

        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(t.getClass().getName(), t.getId());
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Sid sid = new PrincipalSid(userDetails.getUsername());

        final Permission permission = BasePermission.ADMINISTRATION;

        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(objectIdentity);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(objectIdentity);
        }

        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        aclService.updateAcl(acl);

    }
}
