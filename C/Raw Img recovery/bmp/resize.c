/**
 * resize.c
 *
 * Derek H Osborne
 * derek.h.osborne@gmail.com
 *
 * Copy and resize a bmp image by a factor of 1 to 100 inclusive.
 */

#include <ctype.h>       
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "bmp.h"


int main(int argc, char* argv[])
{
    // ensure proper usage
    if (argc != 4)
    {
        printf("Usage: ./copy infile outfile\n");
        return 1;
    }

    // remember filenames
    int factor = atoi(argv[1]);
    char* infile = argv[2];
    char* outfile = argv[3];
    
    // check resize factor contains only intergers
    for (int i = 0, size_factor = strlen(argv[1]) ; i < size_factor; i++)
    {
        if (isdigit(argv[1][i]) == 0)
        {
            printf("factor must be an int\n");
            return 2;
        }
    }

    // check factor is amount is valid 
    if (factor < 0 || factor > 100)
    {
        printf("factor must be 1 to 100 inclusive\n");
        return 3;
    }
    // open input file 
    FILE* inptr = fopen(infile, "r");
    if (inptr == NULL)
    {
        printf("Could not open %s.\n", infile);
        return 4;
    }

    // open output file
    FILE* outptr = fopen(outfile, "w");
    if (outptr == NULL)
    {
        fclose(inptr);
        fprintf(stderr, "Could not create %s.\n", outfile);
        return 5;
    }

    // read infile's BITMAPFILEHEADER
    BITMAPFILEHEADER bf;
    fread(&bf, sizeof(BITMAPFILEHEADER), 1, inptr);

    // read infile's BITMAPINFOHEADER
    BITMAPINFOHEADER bi;
    fread(&bi, sizeof(BITMAPINFOHEADER), 1, inptr);

    // ensure infile is (likely) a 24-bit uncompressed BMP 4.0
    if (bf.bfType != 0x4d42 || bf.bfOffBits != 54 || bi.biSize != 40 || 
        bi.biBitCount != 24 || bi.biCompression != 0)
    {
        fclose(outptr);
        fclose(inptr);
        fprintf(stderr, "Unsupported file format.\n");
        return 6;
    }
    
    // save old dimensions and calc new ones
    int old_height = abs(bi.biHeight);
    int old_width = bi.biWidth;
    int old_padding = (4 - (old_width * sizeof(RGBTRIPLE)) % 4) % 4;
    bi.biWidth = old_width * factor;
    int new_padding = (4 - (bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4;
    bi.biHeight = 0 - abs(old_height) * factor;
    bi.biSizeImage = bi.biWidth * abs(bi.biHeight) * sizeof(RGBTRIPLE) + new_padding * abs(bi.biHeight);
    bf.bfSize = 54 + bi.biSizeImage;

    // write outfile's BITMAPFILEHEADER
    fwrite(&bf, sizeof(BITMAPFILEHEADER), 1, outptr);

    // write outfile's BITMAPINFOHEADER
    fwrite(&bi, sizeof(BITMAPINFOHEADER), 1, outptr);
    
    // iterate over infile's scanlines
    for (int h = 0; h < abs(bi.biHeight); h++)
    {
        if (h % factor != 0)
        {
            fseek(inptr, -((old_width * sizeof(RGBTRIPLE)) + old_padding), SEEK_CUR);
        }
        
       // iterate over pixels in scanline
        for (int j = 0; j < old_width; j++)
        {
            // temporary storage
            RGBTRIPLE triple;

            // read RGB triple from infile
            fread(&triple, sizeof(RGBTRIPLE), 1, inptr);
            
            // vertical resize by re-writing current triple
            for (int k = 0; k < factor; k++)
            {
                // write RGB triple to outfile
                fwrite(&triple, sizeof(RGBTRIPLE), 1, outptr);
            }
        }

        // insert new padding
        for (int l = 0; l < new_padding; l++)
        {
            fputc(0x00, outptr);
        }
        
        // skip over old padding, if any
        fseek(inptr, old_padding, SEEK_CUR);
    }

    // close infile
    fclose(inptr);

    // close outfile
    fclose(outptr);

    // that's all folks
    return 0;
}
