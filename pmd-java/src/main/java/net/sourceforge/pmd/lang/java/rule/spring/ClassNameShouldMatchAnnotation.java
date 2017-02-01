package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;


public class ClassNameShouldMatchAnnotation extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTClassOrInterfaceType node, Object data )
    {
        List<ASTMarkerAnnotation> annotations = node.findDescendantsOfType( ASTMarkerAnnotation.class );
        if ( !annotations.isEmpty() )
        {
            super.addViolation( data, node );
        }
        return super.visit( node, data );
    }

    @Override
    public Object visit ( ASTClassOrInterfaceDeclaration node, Object data )
    {
        List<ASTMarkerAnnotation> annotations = node.findDescendantsOfType( ASTMarkerAnnotation.class );
        if ( !annotations.isEmpty() )
        {
            super.addViolation( data, node );
        }
        return super.visit( node, data );
    }

    @Override
    public Object visit ( ASTMethodDeclaration node, Object data )
    {
        return super.visit( node, data );
    }
}
