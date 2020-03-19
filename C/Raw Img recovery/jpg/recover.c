/**
 * recover.c
 *
 * Derek H Osborne
 * derek.h.osborne@gmail.com
 *
 * Recovers JPEGs from "raw.bmp".
 */
 
#include <cs50.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

// define byte size
typedef uint8_t  BYTE;

int main(void)
{
    // open card.raw
    FILE* infile = fopen("card.raw", "r");
    
     // if card cannot be opened
    if (infile == NULL)
    {
        printf("Could not open card.raw\n");
        return 1;
    }
    
    // create buffer for fread
    BYTE buffer[512];
        
    // init jpeg image pointer
    FILE* jpeg_file = NULL;
    bool already_open = false;
     
    // init image counter
    int counter = 0;
    
    // loop until end of card
    while (!feof(infile))
    {
        // read card.raw into buffer, calculate amount read
        int bytes_read = fread (&buffer, sizeof(BYTE), 512, infile);
        
        // check for jpg indicators
        if ((buffer[0] == 0xff) && (buffer[1] == 0xd8) && (buffer[2] == 0xff) && ((buffer[3] == 0xe0) || (buffer[3] == 0xe1)))
        {
            // check if a jpg is already open 
            if (already_open)
            {
                fclose(jpeg_file);
                counter++;
            }
            
            // open new jpg name it
            char file_name[8];
            sprintf(file_name, "%.3d.jpg", counter);
            jpeg_file = fopen(file_name, "a");
            if (fopen == NULL)
            {
                printf ("Recover could not open output file!\n");
                return 2;
            }
            already_open = true;
 
             // append into image file
            fwrite(&buffer, bytes_read, 1, jpeg_file);
            
        }
        else if (already_open)
        {
            // append into image file
            fwrite(&buffer, bytes_read, 1, jpeg_file); 
        }
    }
    fclose(jpeg_file);
    fclose(infile);
    return 0;
}
