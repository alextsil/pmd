package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


//Based on correct names given to component classes (eg. TaskService, TicketService)s
public class ComponentFieldsShouldBeFinalRule extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTClassOrInterfaceBodyDeclaration declaration, Object data )
    {
        ASTFieldDeclaration fieldDeclaration = declaration.getFirstChildOfType( ASTFieldDeclaration.class );
        if ( fieldDeclaration != null )
        {
            List<ASTClassOrInterfaceType> descendantsOfType = fieldDeclaration.findDescendantsOfType( ASTClassOrInterfaceType.class );
            if ( !descendantsOfType.isEmpty() )
            {
                String className = descendantsOfType.get( 0 ).getImage();
                if ( containsTargetStr( className ) && !fieldDeclaration.isFinal() )
                {
                    super.addViolation( data, declaration );
                }
            }
        }
        return super.visit( declaration, data );
    }

    private boolean containsTargetStr ( String input )
    {
        return StringUtils.containsIgnoreCase( input, "Controller" ) ||
                StringUtils.containsIgnoreCase( input, "Service" ) ||
                StringUtils.containsIgnoreCase( input, "Repository" ) ||
                StringUtils.containsIgnoreCase( input, "Component" );
    }
}
