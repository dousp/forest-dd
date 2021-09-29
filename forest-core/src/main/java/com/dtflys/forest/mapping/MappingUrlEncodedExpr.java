package com.dtflys.forest.mapping;

import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.reflection.ForestMethod;
import com.dtflys.forest.reflection.MetaRequest;
import com.dtflys.forest.utils.StringUtils;
import com.dtflys.forest.utils.URLUtils;

import java.io.UnsupportedEncodingException;

public class MappingUrlEncodedExpr extends MappingExpr {

    private final MappingExpr expr;

    protected MappingUrlEncodedExpr(ForestMethod<?> forestMethod, MappingExpr expr) {
        super(forestMethod, expr.token);
        this.expr = expr;
    }

    @Override
    public boolean isIterateVariable() {
        return expr.isIterateVariable();
    }

    public MappingExpr getExpr() {
        return expr;
    }

    @Override
    public Object render(Object[] args) {
        Object ret = expr.render(args);
        if (ret == null) {
            return null;
        }
        String str = String.valueOf(ret);
        MetaRequest metaRequest = forestMethod.getMetaRequest();
        String charset = null;
        if (metaRequest != null) {
            charset = metaRequest.getCharset();
        }
        Object encoded = null;
        if (StringUtils.isNotBlank(charset)) {
            try {
                encoded = URLUtils.forceEncode(str, charset);
            } catch (UnsupportedEncodingException e) {
                throw new ForestRuntimeException(e);
            }
        }
        if (encoded == null) {
            try {
                encoded = URLUtils.forceEncode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new ForestRuntimeException(e);
            }
        }
        return encoded;
    }
}