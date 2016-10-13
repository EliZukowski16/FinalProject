package org.ssa.ironyard.liquorstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.ssa.ironyard.liquorstore.dao.orm.ORMCoreProductImpl;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;

import com.mysql.cj.api.jdbc.Statement;

@Repository
public class DAOCoreProductImpl extends AbstractDAOCoreProduct implements DAOCoreProduct
{

    @Autowired
    public DAOCoreProductImpl(DataSource dataSource)
    {
        super(new ORMCoreProductImpl(), dataSource);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, CoreProduct domainToInsert) throws SQLException
    {
        insertStatement.setString(1, domainToInsert.getName());
        insertStatement.setString(2, domainToInsert.getType().toString());
        insertStatement.setString(3, domainToInsert.getSubType());
        insertStatement.setString(4, domainToInsert.getDescription());
    }
    
    

    @Override
    public CoreProduct insertTag(CoreProduct domain)
    {
        if(domain.getId() != null)
        {
            List<Tag> tags = new ArrayList<>();

            for (Tag t : domain.getTags())
            {

                KeyHolder generatedId = new GeneratedKeyHolder();
                if (this.springTemplate.update((Connection conn) ->
                {
                    PreparedStatement statement = conn.prepareStatement(
                            ((ORMCoreProductImpl) this.orm).prepareInsertTag(), Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, domain.getId());
                    statement.setString(2, t.getName());
                    return statement;
                }, generatedId) == 1)
                {
                    tags.add(new Tag(t.getName()));
                }
            }

            CoreProduct copy = (CoreProduct) domain.clone();

            copy.setTags(tags);

            return copy;
        }
        
        return null;
    }
    
    @Override
    public CoreProduct insert(CoreProduct domain)
    {
        CoreProduct coreProduct;

        if ((coreProduct = super.insert(domain)) != null)
        {
            return this.insertTag(coreProduct);
        }

        return null;
    }

    @Override
    protected CoreProduct afterInsert(CoreProduct copy, Integer id)
    {
        CoreProduct coreProduct;
        coreProduct = new CoreProduct(id, copy.getName(), copy.getTags(), copy.getType(), copy.getSubType(),
                copy.getDescription(), true);

        return coreProduct;
    }

    @Override
    protected CoreProduct afterUpdate(CoreProduct copy)
    {
        CoreProduct coreProduct;
        coreProduct = new CoreProduct(copy.getId(), copy.getName(), copy.getTags(), copy.getType(), copy.getSubType(),
                copy.getDescription(), true);

        return coreProduct;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(CoreProduct domainToUpdate)
    {
        return new PreparedStatementSetter()
        {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                
                ps.setString(1, domainToUpdate.getName());
                ps.setString(2, domainToUpdate.getType().toString());
                ps.setString(3, domainToUpdate.getSubType());
                ps.setString(4, domainToUpdate.getDescription());
                ps.setInt(5, domainToUpdate.getId());

            }

        };
    }

}
