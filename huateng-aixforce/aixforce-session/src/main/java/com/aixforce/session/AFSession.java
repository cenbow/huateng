package com.aixforce.session;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class AFSession implements HttpSession {

    private volatile long lastAccessedAt;

    private final AFSessionManager sessionManager;

    private final String id;

    private final long createdAt;

    private final HttpServletRequest request;

    private int maxInactiveInterval;

    //new setted attributes
    private final Map<String, Object> newAttributes = Maps.newHashMapWithExpectedSize(5);

    //deleted attributes
    private final Set<String> deleteAttribute = Sets.newHashSetWithExpectedSize(5);

    private final Map<String, Object> dbSession;

    private volatile boolean invalid;  //true for delete the session in redis

    private volatile boolean dirty; //true for flush to redis;

    public AFSession(AFSessionManager sessionManager, HttpServletRequest request, String id) {
        this.request = request;
        this.sessionManager = sessionManager;
        this.id =id;
        this.dbSession = loadDBSession();
        this.createdAt = System.currentTimeMillis();
        this.lastAccessedAt = createdAt;
    }

    private Map<String, Object> loadDBSession() {
        return this.sessionManager.findSessionById(this.id);
    }

    /**
     * Returns the time when this session was created, measured
     * in milliseconds since midnight January 1, 1970 GMT.
     *
     * @return a <code>long</code> specifying
     *         when this session was created,
     *         expressed in
     *         milliseconds since 1/1/1970 GMT
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public long getCreationTime() {
        return createdAt;
    }

    /**
     * Returns a string containing the unique identifier assigned
     * to this session. The identifier is assigned
     * by the servlet container and is implementation dependent.
     *
     * @return a string specifying the identifier
     *         assigned to this session
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Returns the last time the client sent a request associated with
     * this session, as the number of milliseconds since midnight
     * January 1, 1970 GMT, and marked by the time the container received the
     * request.
     * <p/>
     * <p>Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access
     * time.
     *
     * @return a <code>long</code>
     *         representing the last time
     *         the client sent a request associated
     *         with this session, expressed in
     *         milliseconds since 1/1/1970 GMT
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedAt;
    }

    /**
     * Returns the ServletContext to which this session belongs.
     *
     * @return The ServletContext object for the web application
     * @since Servlet 2.3
     */
    @Override
    public ServletContext getServletContext() {
        return this.request.getServletContext();
    }

    /**
     * Specifies the time, in seconds, between client requests before the
     * servlet container will invalidate this session.
     * <p/>
     * <p>An <tt>interval</tt> value of zero or less indicates that the
     * session should never timeout.
     *
     * @param interval An integer specifying the number
     *                 of seconds
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    /**
     * Returns the maximum time interval, in seconds, that
     * the servlet container will keep this session open between
     * client accesses. After this interval, the servlet container
     * will invalidate the session.  The maximum time interval can be set
     * with the <code>setMaxInactiveInterval</code> method.
     * <p/>
     * <p>A return value of zero or less indicates that the
     * session will never timeout.
     *
     * @return an integer specifying the number of
     *         seconds this session remains open
     *         between client requests
     * @see #setMaxInactiveInterval
     */
    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    /**
     * @deprecated As of Version 2.1, this method is
     *             deprecated and has no replacement.
     *             It will be removed in a future
     *             version of the Java Servlet API.
     */
    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    /**
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     *
     * @param name a string specifying the name of the object
     * @return the object with the specified name
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public Object getAttribute(String name) {
        checkValid();
        if (newAttributes.containsKey(name)) {
            return newAttributes.get(name);
        } else if (deleteAttribute.contains(name)) {
            return null;
        }
        return dbSession.get(name);
    }

    /**
     * @param name a string specifying the name of the object
     * @return the object with the specified name
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @deprecated As of Version 2.2, this method is
     *             replaced by {@link #getAttribute}.
     */
    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    /**
     * Returns an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session.
     *
     * @return an <code>Enumeration</code> of
     *         <code>String</code> objects specifying the
     *         names of all the objects bound to
     *         this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        checkValid();
        Set<String> names = Sets.newHashSet(dbSession.keySet());
        names.addAll(newAttributes.keySet());
        names.removeAll(deleteAttribute);
        return Collections.enumeration(names);
    }

    /**
     * @return an array of <code>String</code>
     *         objects specifying the
     *         names of all the objects bound to
     *         this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @deprecated As of Version 2.2, this method is
     *             replaced by {@link #getAttributeNames}
     */
    @Override
    public String[] getValueNames() {
        checkValid();
        Set<String> names = Sets.newHashSet(dbSession.keySet());
        names.addAll(newAttributes.keySet());
        names.removeAll(deleteAttribute);
        return names.toArray(new String[0]);
    }

    /**
     * Binds an object to this session, using the name specified.
     * If an object of the same name is already bound to the session,
     * the object is replaced.
     * <p/>
     * <p>After this method executes, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueBound</code>. The container then
     * notifies any <code>HttpSessionAttributeListener</code>s in the web
     * application.
     * <p/>
     * <p>If an object was already bound to this session of this name
     * that implements <code>HttpSessionBindingListener</code>, its
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     * <p/>
     * <p>If the value passed in is null, this has the same effect as calling
     * <code>removeAttribute()<code>.
     *
     * @param name  the name to which the object is bound;
     *              cannot be null
     * @param value the object to be bound
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public void setAttribute(String name, Object value) {
        checkValid();
        if (value != null) {
            newAttributes.put(name, value);
            deleteAttribute.remove(name);
        } else {
            deleteAttribute.add(name);
            newAttributes.remove(name);
        }
        dirty = true;
    }

    /**
     * @param name  the name to which the object is bound;
     *              cannot be null
     * @param value the object to be bound; cannot be null
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @deprecated As of Version 2.2, this method is
     *             replaced by {@link #setAttribute}
     */
    @Override
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    /**
     * Removes the object bound with the specified name from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     * <p/>
     * <p>After this method executes, and if the object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueUnbound</code>. The container
     * then notifies any <code>HttpSessionAttributeListener</code>s in the web
     * application.
     *
     * @param name the name of the object to
     *             remove from this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public void removeAttribute(String name) {
        checkValid();
        deleteAttribute.add(name);
        newAttributes.remove(name);
        dirty = true;
    }

    /**
     * @param name the name of the object to
     *             remove from this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @deprecated As of Version 2.2, this method is
     *             replaced by {@link #removeAttribute}
     */
    @Override
    public void removeValue(String name) {
        removeAttribute(name);
        dirty = true;
    }

    /**
     * Invalidates this session then unbinds any objects bound
     * to it.
     *
     * @throws IllegalStateException if this method is called on an
     *                               already invalidated session
     */
    @Override
    public void invalidate() {
        invalid = true;
        dirty=true;
        sessionManager.deletePhysically(this.getId());
    }

    /**
     * Returns <code>true</code> if the client does not yet know about the
     * session or if the client chooses not to join the session.  For
     * example, if the server used only cookie-based sessions, and
     * the client had disabled the use of cookies, then a session would
     * be new on each request.
     *
     * @return <code>true</code> if the
     *         server has created a session,
     *         but the client has not yet joined
     * @throws IllegalStateException if this method is called on an
     *                               already invalidated session
     */
    @Override
    public boolean isNew() {
        return true;
    }

    /**
     * asserts that the session is valid
     */
    protected void checkValid() throws IllegalStateException {
        Preconditions.checkState(!invalid);
    }

    public boolean isDirty() {
        return dirty;
    }

    public Map<String, Object> snapshot() {
        Map<String, Object> snap = Maps.newHashMap();
        snap.putAll(dbSession);
        snap.putAll(newAttributes);
        for (String name : deleteAttribute) {
            snap.remove(name);
        }
        return snap;
    }

    public boolean isValid() {
        return !invalid;
    }
}
