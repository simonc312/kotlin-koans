package ii_collections

fun Shop.getCustomersWhoOrderedProduct(product: Product): Set<Customer> {
    // Return the set of customers who ordered the specified product
    return customers.filter { c -> c.orderedProducts.contains(product)}.toSet()
}

fun Customer.getMostExpensiveDeliveredProduct(): Product? {
    // Return the most expensive product among all delivered products
    // (use the Order.isDelivered flag)
    return orders.filter(Order::isDelivered)
                .flatMap(Order::products)
                .maxBy(Product::price)
}

fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    // Return the number of times the given product was ordered.
    // Note: a customer may order the same product for several times.
    var productOrderCount = 0
    return customers.fold(productOrderCount, { count: Int, customer: Customer ->  count.plus(customer.orderCountOfProduct(product))} )
}

private fun Customer.orderCountOfProduct(product: Product): Int {
    return orders.flatMap(Order::products).count{p -> p == product}
}
