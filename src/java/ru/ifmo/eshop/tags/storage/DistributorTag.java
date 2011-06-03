package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Distributor;
import ru.ifmo.eshop.storage.StorageManager;

public class DistributorTag extends RecordTag {

    @Override
    public int doStartTag() throws JspException {
        Distributor d;
        if (keyId==-1) {
            DistributorsTag tag=(DistributorsTag)
                    RecordTag.getAncestor(this,
                    "ru.ifmo.eshop.tags.storage.DistributorsTag");
            if (tag==null) {
                throw new JspException("No parent DistributorsTag found");
            }
            d=tag.fetchDistributor();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                d = sm.getDistributor(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (d==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", d.getId());
        fieldMap.put("type",d.getType());
        fieldMap.put("title",d.getTitle());
        fieldMap.put("country", d.getCountry());
        return EVAL_BODY_INCLUDE;
    }
}