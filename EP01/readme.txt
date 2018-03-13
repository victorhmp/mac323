/******************************************************************************
 *  
 *  Name: Victor Hugo Miranda Pinto   
 *  NUSP: 10297720
 *
 ******************************************************************************/

Programming Assignment 2: Deques and Randomized Queues


/******************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 *****************************************************************************/
    Para implementação da Deque, utilizei uma doubly linked-list, onde cada nó da lista
continha referências para o próximo nó e para o anterior. Essa escolha permitiu realizar
mais facilmente operações no final da lista, que eram complexas demais quando usando uma lista
com ligações simples.
    Para implementação da Randomized Queue, utilizei arrays de tamanho dinâmico, ou seja,
ajustava o tamanho do array que guardava os itens da Queue conforme necessário. O uso dessas
estruturas de dados permitiu o acesso aleatório aos itens da Queue enquanto mantendo a performance
desejada para a API. Para iterar de maneira aleatória pela Queue, copio o array original e 
embaralho a cópia, depois iterando normalmente.

/******************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 *****************************************************************************/
 Sendo n o número de itens (ou nós) na Deque ou na Queue:

Randomized Queue:   ~  24 + K*n  bytes, sendo k o número de bytes necessários para
guardar um item de tipo definido no construtor da Queue.
(16 bytes object overhead, 4 bytes tamanho do array, 4 bytes padding)

Deque:              ~  48n  bytes
(16 bytes object overhead, 8 bytes for each reference to Item and Nodes first and last,
8 bytes extra overhead)


/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
    Aparentemente bugs nos métodos addLast() e removeFirst() de Deque.java, apontados
pelo corretor automático.

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
