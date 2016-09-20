package cn.com.huateng.web.filter;

import com.google.common.base.Objects;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: 董培基
 * Date: 13-12-31
 * Time: 下午2:18
 */
public class HuatengCharacterEncodingFilter extends OncePerRequestFilter{
    private String encoding;

    private boolean forceEncoding = false;


    /**
     * Set the encoding to use for requests. This encoding will be passed into a
     * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call.
     * <p>Whether this encoding will override existing request encodings
     * (and whether it will be applied as default response encoding as well)
     * depends on the {@link #setForceEncoding "forceEncoding"} flag.
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Set whether the configured {@link #setEncoding encoding} of this filter
     * is supposed to override existing request and response encodings.
     * <p>Default is "false", i.e. do not modify the encoding if
     * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()}
     * returns a non-null value. Switch this to "true" to enforce the specified
     * encoding in any case, applying it as default response encoding as well.
     * <p>Note that the response encoding will only be set on Servlet 2.4+
     * containers, since Servlet 2.3 did not provide a facility for setting
     * a default response encoding.
     */
    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
            if(Objects.equal(uri,"/cash/cashBankNotify.html")){
                request.setCharacterEncoding("GBK");
            }else{
                request.setCharacterEncoding(this.encoding);
            }
            if(uri.contains("/21cn")){
                response.setHeader("P3P","CP=CAO PSA OUR");
            }
            if (this.forceEncoding) {
                response.setCharacterEncoding(this.encoding);
            }
        }
        filterChain.doFilter(request, response);
    }
}

