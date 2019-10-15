set title "ArrayList VS LinkedList(LogPlot)" font "Times ,15"
set xlabel 'Position' font "Times ,12"
set ylabel 'Computation Time in LogScale(ns)'

set logscale y 10

set style line 1 \
    linecolor rgb '#0060ad' \
    linetype 1 linewidth 2 \
    pointtype 7 pointsize 1.5

set style line 2 \
    linecolor rgb '#dd181f' \
    linetype 1 linewidth 2 \
    pointtype 5 pointsize 1.5

set style line 3 \
    linecolor rgb '#35c2ee' \
    linetype 1 linewidth 2 \
    pointtype 7 pointsize 1.5

set style line 4 \
    linecolor rgb '#df7c19' \
    linetype 1 linewidth 2 \
    pointtype 5 pointsize 1.5

plot 'arrayInsert.dat' using 1:xtic(2) with linespoints linestyle 1 title "ArrayList(insert)", \
     'linkedInsert.dat'using 1:xtic(2) with linespoints linestyle 2 title "LinkedList(insert)", \
     'arrayDelete.dat' using 1:xtic(2) with linespoints linestyle 3 title "ArrayList(delete)", \
     'linkedDelete.dat'using 1:xtic(2) with linespoints linestyle 4 title "LinkedList(delete)", \
