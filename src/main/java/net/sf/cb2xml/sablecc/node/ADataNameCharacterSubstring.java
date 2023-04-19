/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.cb2xml.sablecc.node;

import net.sf.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ADataNameCharacterSubstring extends PCharacterSubstring
{
    private TDataName _dataName_;

    public ADataNameCharacterSubstring()
    {
        // Constructor
    }

    public ADataNameCharacterSubstring(
        @SuppressWarnings("hiding") TDataName _dataName_)
    {
        // Constructor
        setDataName(_dataName_);

    }

    @Override
    public Object clone()
    {
        return new ADataNameCharacterSubstring(
            cloneNode(this._dataName_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADataNameCharacterSubstring(this);
    }

    public TDataName getDataName()
    {
        return this._dataName_;
    }

    public void setDataName(TDataName node)
    {
        if(this._dataName_ != null)
        {
            this._dataName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dataName_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._dataName_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._dataName_ == child)
        {
            this._dataName_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._dataName_ == oldChild)
        {
            setDataName((TDataName) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
