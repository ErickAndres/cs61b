/*
 * CS61C Summer 2014
 * Name: Peter Perfect
 * Login: cs61c
 */

#include <stdio.h>
#include <stdlib.h>

/* Function declarations. You may declare additional functions here. */
void usage(void);

/* Global variables. rows is the number of successive generations (after
 * the initial) you will create and print. rule is a 32-bit integer specifying
 * the rule to use when computing aliveness/deadness. */
unsigned int rows, rule;

/** Determines if the the array postion POS will be alive in the next generation
 *  given current life POP. */
char will_live(char *pop, int pos);
/** Prints the first CELLS of LIFE in a single row, as per the hw spec. */
void print_row(char *life, int cells);


int main(int argc,char *argv[]) {
    // You may declare additional local variables here
    char **endptr, *rowA, *rowB;

    /* Perform input checking on arguments. First checks for correct number of arguments, and
     * then uses the strtoul() function to convert each argument into an unsigned int. Also
     * checks whether each argument is an integer or not. If any of these requirements are
     * not met, print the usage string instead.
     */
    if(argc != 3)       usage();
    rows = (unsigned int) strtoul(argv[1],(endptr = argv),10);
    if(**endptr)        usage();
    rule = (unsigned int) strtoul(argv[2],(endptr = argv),10);
    if(**endptr)        usage();

    /* Print details of this run of Life1D. Each run of Life1D must begin with this.
     * Do not modify or delete this line. */
    printf("P1 %u %u ## %u rows of Life1D (Rule %u)\n",2*rows+1,rows+1,rows,rule);

    /* Initializes two segments of memory of 0s, each with space for 2*rows+5 chars, for you
     * to use. Each contains enough space for one generation as well as two-element
     * padding on each side, which you may choose to or not to use. We use calloc()
     * because it sets each element to 0. */
    rowA = (char *) calloc(2*rows+5, sizeof(char));
    rowB = (char *) calloc(2*rows+5, sizeof(char));

    /* Check to see that memory allocation has been successful. If not, exit. */
    if( !rowA || !rowB ) {
        fprintf(stderr, "Out of memory.\n");
        exit(1);
    }

    /* You must now generate and print the initial generation and each successive generation.
     * Make sure your format follows the specifications.*/
    char *A = rowA + 2, *B = rowB + 2;
    const int NUM_CELLS = 2 * rows + 1;

    /* Deal with initial row. */
    A[rows] = 1; // not (rows + 1), because of 0-indexing
    print_row(A, NUM_CELLS);

    /* Deal with following rows. */
    for (int i = 0; i < rows; i += 1) {
        for (int j = 0; j < NUM_CELLS; j += 1) {
            B[j] = will_live(A, j);
        }
        print_row(B, NUM_CELLS);

        /* Swap A and B. */
        char *tmp = A;
        A = B;
        B = tmp;
    }
    /* Frees initialized memory and exit successfully. */
    free(rowA);
    free(rowB);
    return 0;
}

/*
 * Prints the usage text if inputs do not satisfy the valid constraints. Do not modify
 * this function.
 */
void usage(void) {
    printf("Usage: Life1D <rows> <rule>\n");
    printf("    This program simulates 1D Life: the simplest class of one-dimensional\n");
    printf("    cellular automata in a <ROWS=rows+1> x <COLS=2*rows+1> grid starting\n");
    printf("    with a single live cell in the middle of the top row using rule <rule>.\n");
    printf("    These 1D rules are defined in Wolfram's Elementary Cellular Automata:\n");
    printf("    http://mathworld.wolfram.com/ElementaryCellularAutomaton.html\n");
    printf("    This program will print to stdout data in plain PBM file format.\n");
    printf("    This output can be easily viewed using the display command or \n");
    printf("    converted to a another format using the pbmto* and ppmto* utilities. \n");
    printf("    A plain ASCII PBM file can be created by adding a header line \n");
    printf("    \"P1 <WIDTH> <HEIGHT>\" and followed by a grid of data \n");
    printf("    (0 = dead = white, 1 = live = black). Add a comment on the first \n");
    printf("    line with a brief description of the image.\n");
    printf("  Arguments:\n");
    printf("    <rows> is a positive integer specifying the number of rows to generate\n");
    printf("    (not counting the first \"seed row\" which is all dead except for a central\n");
    printf("    live cell). The columns are computed automatically -- enough so that\n");
    printf("    the rule, if it were to grow in the normal triangular pattern, would\n");
    printf("    just perfectly reach the edge. Off the board is considerered \"dead\".\n");
    printf("    <rule> is a number from 0-4294967295 specifying the rule to use.\n");

    exit(0);
}

char will_live(char *pop, int pos) {
    unsigned rule_pos = 0;
    for (int i = -2; i <= 2; i += 1) {
        rule_pos <<= 1;
        rule_pos += pop[pos + i];
    }
    return (rule >> rule_pos) & 1;
}

void print_row(char *life, int cells) {
    for (int i = 0; i < cells - 1; i += 1) {
        printf("%d ", life[i]);
    }
    printf("%d\n", life[cells - 1]);
}