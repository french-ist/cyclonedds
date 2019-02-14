
#ifndef OSAPI_EXPORT_H
#define OSAPI_EXPORT_H

#ifdef OSAPI_STATIC_DEFINE
#  define OSAPI_EXPORT
#  define OSAPI_NO_EXPORT
#else
#  ifndef OSAPI_EXPORT
#    ifdef OSAPI_EXPORTS
        /* We are building this library */
#      define OSAPI_EXPORT 
#    else
        /* We are using this library */
#      define OSAPI_EXPORT 
#    endif
#  endif

#  ifndef OSAPI_NO_EXPORT
#    define OSAPI_NO_EXPORT 
#  endif
#endif

#ifndef OSAPI_DEPRECATED
#  define OSAPI_DEPRECATED __attribute__ ((__deprecated__))
#endif

#ifndef OSAPI_DEPRECATED_EXPORT
#  define OSAPI_DEPRECATED_EXPORT OSAPI_EXPORT OSAPI_DEPRECATED
#endif

#ifndef OSAPI_DEPRECATED_NO_EXPORT
#  define OSAPI_DEPRECATED_NO_EXPORT OSAPI_NO_EXPORT OSAPI_DEPRECATED
#endif

#if 0 /* DEFINE_NO_DEPRECATED */
#  ifndef OSAPI_NO_DEPRECATED
#    define OSAPI_NO_DEPRECATED
#  endif
#endif

#endif /* OSAPI_EXPORT_H */
