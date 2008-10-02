package com.intellij.psi.impl.source;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.PsiClassStub;
import com.intellij.psi.impl.light.LightClassReference;
import org.jetbrains.annotations.NotNull;

public class PsiEnumConstantInitializerImpl extends PsiClassImpl implements PsiEnumConstantInitializer {
  private static final Logger LOG = Logger.getInstance("#com.intellij.psi.impl.source.PsiEnumConstantInitializerImpl");
  private PsiClassType myCachedBaseType = null;

  public PsiEnumConstantInitializerImpl(final PsiClassStub stub) {
    super(stub);
  }

  public PsiEnumConstantInitializerImpl(final ASTNode node) {
    super(node);
  }


  protected Object clone() {
    PsiEnumConstantInitializerImpl clone = (PsiEnumConstantInitializerImpl)super.clone();
    clone.myCachedBaseType = null;
    return clone;
  }

  public void subtreeChanged() {
    super.subtreeChanged();
    myCachedBaseType = null;
  }

  public PsiExpressionList getArgumentList() {
    PsiElement parent = getParent();
    LOG.assertTrue(parent instanceof PsiEnumConstant);
    return ((PsiCall)parent).getArgumentList();
  }

  public boolean isInQualifiedNew() {
    return false;
  }


  @NotNull
  public PsiJavaCodeReferenceElement getBaseClassReference() {
    PsiClass containingClass = getBaseClass();
    return new LightClassReference(getManager(), containingClass.getName(), containingClass);
  }

  private PsiClass getBaseClass() {
    PsiElement parent = getParent();
    LOG.assertTrue(parent instanceof PsiEnumConstant);
    PsiClass containingClass = ((PsiEnumConstant)parent).getContainingClass();
    LOG.assertTrue(containingClass != null);
    return containingClass;
  }

  public PsiElement getParent() {
    return getParentByStub();
  }

  @NotNull
  public PsiEnumConstant getEnumConstant() {
    return (PsiEnumConstant) getParent();
  }

  @NotNull
  public PsiClassType getBaseClassType() {
    if (myCachedBaseType == null) {
      myCachedBaseType = JavaPsiFacade.getInstance(getProject()).getElementFactory().createType(getBaseClass());
    }
    return myCachedBaseType;
  }

  public PsiIdentifier getNameIdentifier() {
    return null;
  }

  public String getQualifiedName() {
    return null;
  }

  public PsiModifierList getModifierList() {
    return null;
  }

  public boolean hasModifierProperty(@NotNull String name) {
    return false;
  }

  public PsiReferenceList getExtendsList() {
    return null;
  }

  public PsiReferenceList getImplementsList() {
    return null;
  }

  @NotNull
  public PsiClassType[] getSuperTypes() {
    return new PsiClassType[]{getBaseClassType()};
  }

  public boolean isInterface() {
    return false;
  }

  public boolean isAnnotationType() {
    return false;
  }

  public boolean isEnum() {
    return false;
  }

  public PsiTypeParameterList getTypeParameterList() {
    return null;
  }

  public PsiElement getOriginalElement() {
    return this;
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaElementVisitor) {
      ((JavaElementVisitor)visitor).visitEnumConstantInitializer(this);
    }
    else {
      visitor.visitElement(this);
    }
  }

  public String toString() {
    return "PsiAnonymousClass (PsiEnumConstantInitializerImpl)):";
  }
}