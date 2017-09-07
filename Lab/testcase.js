QUnit.test('find shortest path', (assert) => {

	const graph = new UndirectedGraph(); 
	graph.addVertex('a'); 
	graph.addVertex('b'); 
	graph.addVertex('c'); 
	graph.addVertex('d'); 
	graph.addVertex('e'); 
	
	graph.addEdge('a', new UndirectedEdge(5), 'b');
	graph.addEdge('a', new UndirectedEdge(5), 'c');
	graph.addEdge('a', new UndirectedEdge(5), 'd');
	graph.addEdge('b', new UndirectedEdge(5), 'c');
	graph.addEdge('b', new UndirectedEdge(5), 'd');
	graph.addEdge('c', new UndirectedEdge(5), 'd');
	graph.addEdge('c', new UndirectedEdge(5), 'e');
	graph.addEdge('d', new UndirectedEdge(5), 'e');
	
	assert.deepEqual(shortestUndirectedPath(graph, 'a', (vertex) => vertex === 'e'), ['a', 'd', 'e']);
});
