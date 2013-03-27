learning_overtone
=================

Learning how to use overtone.  You probably don't want to fork this
code.

I strongly suspect there are much much better ways to do all of
what I am attempting to do with this code : the general idea is that I
want a quick and dirty toolkit to loop (mostly rhythmic) chord
progressions over a variety of instruments with a variety of key
signatures.  I believe that, in many of the places in which I am
writing functions to play a single note, I should, perhaps, be
generating a new instrument.  I am not sure of that however.  I have
not sufficiently grokked overtone/supercollider to know what
advantages and costs defining a new instrument gives.

My dream, of course, was that I could write something to fade
different variants of standard loops in and out in order to
automatically generate a continuous stream of mindless techno beats
(which, of course, I would listen to while I wrote other code).  This
goal is quite a long way off.

Please note that a good chunk of my instrument definitions were stolen
mindlessly (I am beginning to sound like the anti-Buddha with all my
mindlessness) from
https://github.com/overtone/overtone/wiki/Oscillators
I fully expect to 'borrow' more instrument definitions from other
sources.  Perhaps, if I feel particularly 'responsible' in the future,
I will isolate them all in a separate file.
