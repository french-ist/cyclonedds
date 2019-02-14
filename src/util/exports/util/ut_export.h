
#ifndef UTIL_EXPORT_H
#define UTIL_EXPORT_H

#ifdef UTIL_STATIC_DEFINE
#  define UTIL_EXPORT
#  define UTIL_NO_EXPORT
#else
#  ifndef UTIL_EXPORT
#    ifdef util_EXPORTS
        /* We are building this library */
#      define UTIL_EXPORT 
#    else
        /* We are using this library */
#      define UTIL_EXPORT 
#    endif
#  endif

#  ifndef UTIL_NO_EXPORT
#    define UTIL_NO_EXPORT 
#  endif
#endif

#ifndef UTIL_DEPRECATED
#  define UTIL_DEPRECATED __attribute__ ((__deprecated__))
#endif

#ifndef UTIL_DEPRECATED_EXPORT
#  define UTIL_DEPRECATED_EXPORT UTIL_EXPORT UTIL_DEPRECATED
#endif

#ifndef UTIL_DEPRECATED_NO_EXPORT
#  define UTIL_DEPRECATED_NO_EXPORT UTIL_NO_EXPORT UTIL_DEPRECATED
#endif

#if 0 /* DEFINE_NO_DEPRECATED */
#  ifndef UTIL_NO_DEPRECATED
#    define UTIL_NO_DEPRECATED
#  endif
#endif

#endif /* UTIL_EXPORT_H */
