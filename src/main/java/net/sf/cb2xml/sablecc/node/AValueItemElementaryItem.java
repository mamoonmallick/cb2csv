/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.cb2xml.sablecc.node;

import net.sf.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AValueItemElementaryItem extends PElementaryItem
{
    private PValueItem _valueItem_;

    public AValueItemElementaryItem()
    {
        // Constructor
    }

    public AValueItemElementaryItem(
        @SuppressWarnings("hiding") PValueItem _valueItem_)
    {
        // Constructor
        setValueItem(_valueItem_);

    }

    @Override
    public Object clone()
    {
        return new AValueItemElementaryItem(
            cloneNode(this._valueItem_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAValueItemElementaryItem(this);
    }

    public PValueItem getValueItem()
    {
        return this._valueItem_;
    }

    public void setValueItem(PValueItem node)
    {
        if(this._valueItem_ != null)
        {
            this._valueItem_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._valueItem_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._valueItem_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._valueItem_ == child)
        {
            this._valueItem_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._valueItem_ == oldChild)
        {
            setValueItem((PValueItem) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
