# Evaluation lab - Apache Kafka

## Group number: 3

## Group members

- Carlo Ronconi
- Giulia Prosio
- Kaixi Matteo Chen

## Exercise 1

- Number of partitions allowed for inputTopic (1, n)
- Number of consumers allowed (1, n)
    - Consumer 1: <groupA>
    - Consumer 2: <groupA>
    - ...
    - Consumer n: <groupA>


## Exercise 2

- Number of partitions allowed for inputTopic (1, n)
- Number of consumers allowed (1 ,1)
    - Consumer 1: <groupB>

## Considerations

Regarding the first exercise, increasing the number of consumers instances over the
partition number leads to some consumers with no work to do.

The consumer group between the two applications (AtMostOncePrinter,
PopularTopicConsumer) must be different in order to ensure that both of them
receive all the topic's data.

Exercise one can be parallelised while the second one cannot due to the necessity of the popularity map.

In order to parallelise the second application, different methods can be exploited as:
- Use a `kafka` topic to store the popularity count that can be polled and updated by different consumer instances
- Use `kafka` built in `log comaction` to implement the popularity map 
