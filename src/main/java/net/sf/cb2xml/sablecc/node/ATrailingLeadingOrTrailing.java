/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.cb2xml.sablecc.node;

import net.sf.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ATrailingLeadingOrTrailing extends PLeadingOrTrailing
{
    private TTrailing _trailing_;

    public ATrailingLeadingOrTrailing()
    {
        // Constructor
    }

    public ATrailingLeadingOrTrailing(
        @SuppressWarnings("hiding") TTrailing _trailing_)
    {
        // Constructor
        setTrailing(_trailing_);

    }

    @Override
    public Object clone()
    {
        return new ATrailingLeadingOrTrailing(
            cloneNode(this._trailing_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATrailingLeadingOrTrailing(this);
    }

    public TTrailing getTrailing()
    {
        return this._trailing_;
    }

    public void setTrailing(TTrailing node)
    {
        if(this._trailing_ != null)
        {
            this._trailing_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._trailing_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._trailing_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._trailing_ == child)
        {
            this._trailing_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._trailing_ == oldChild)
        {
            setTrailing((TTrailing) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
