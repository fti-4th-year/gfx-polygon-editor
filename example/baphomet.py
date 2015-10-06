import math

cx = 400
cy = 300
r = 256

print 2

print "Polygon"
for i in xrange(5):
	print "%d %d" % (cx + r*math.sin(2*math.pi*i*2/5), cy + r*math.cos(2*math.pi*i*2/5))
print

print "Polygon"
steps = 64
for i in xrange(steps):
	print "%d %d" % (cx + r*math.sin(2*math.pi*i/steps), cy + r*math.cos(2*math.pi*i/steps))
print
