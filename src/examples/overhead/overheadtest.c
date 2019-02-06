#include "ddsc/dds.h"
#include "Overhead.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <inttypes.h>

static int cmp_double (const void *va, const void *vb)
{
  const double *a = va;
  const double *b = vb;
  return (*a == *b) ? 0 : (*a < *b) ? -1 : 1;
}

int main (int argc, char **argv)
{
  dds_entity_t participant, topic, writer, reader;
  DataType d;
  void *samples[1] = { &d };
  dds_sample_info_t info[1];
  int status;

  (void) argc;
  (void) argv;

  participant = dds_create_participant (DDS_DOMAIN_DEFAULT, NULL, NULL);
  DDS_ERR_CHECK (participant, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
  topic = dds_create_topic (participant, &DataType_desc, "Overhead", NULL, NULL);
  DDS_ERR_CHECK (topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
  writer = dds_create_writer (participant, topic, NULL, NULL);
  DDS_ERR_CHECK (writer, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
  reader = dds_create_reader (participant, topic, NULL, NULL);
  DDS_ERR_CHECK (reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

  printf ("%7s WRITE %6s %6s %6s %6s | READ %6s %6s %6s %6s\n", "size", "min", "median", "99%", "max", "min", "median", "99%", "max");
  for (size_t size = 0; size <= 1048576; size = (size == 0) ? 1 : 2 * size)
  {
    d.payload._buffer = size ? dds_alloc (size) : NULL;
    d.payload._length = d.payload._maximum = (uint32_t) size;
    d.payload._release = 1;
    if (size)
      memset (d.payload._buffer, 0, size);

    double twrite[1001]; /* in us */
    double tread[sizeof (twrite) / sizeof (twrite[0])];  /* in us */
    const int rounds = (int) (sizeof (twrite) / sizeof (*twrite));
    for (int k = 0; k < rounds; k++)
    {
      const dds_time_t twrite1 = dds_time ();
      status = dds_write (writer, &d);
      DDS_ERR_CHECK (status, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
      const dds_time_t tread1 = dds_time ();
      status = dds_read (reader, samples, info, 1, 1);
      DDS_ERR_CHECK (status, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
      if (status != 1) abort ();
      tread[k] = (dds_time () - tread1) / 1e3;
      twrite[k] = (tread1 - twrite1) / 1e3;
    }

    dds_free (d.payload._buffer);

    qsort (twrite, rounds, sizeof (*twrite), cmp_double);
    qsort (tread, rounds, sizeof (*tread), cmp_double);
    assert (rounds >= 100); /* for 99% case */
    printf ("%7zu %5s %6.0f %6.0f %6.0f %6.0f | %4s %6.0f %6.0f %6.0f %6.0f\n",
            size,
            "", twrite[0], twrite[rounds / 2], twrite[rounds - rounds / 100], twrite[rounds - 1],
            "", tread[0], tread[rounds / 2], tread[rounds - rounds / 100], tread[rounds - 1]);
  }

  dds_delete (participant);
  return 0;
}
