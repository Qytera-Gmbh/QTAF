<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script>
    function toggleScenarioInformation(scenarioId) {
        console.log(`Toggle ${scenarioId}}`);
        $(`div.scenario-information[scenario-id='${scenarioId}}']`).toggle();
    }

    $(document).ready(function() {
        console.log("Document ready")

        $("span.toggleScenarioInformation").click(function() {
            console.log("Click", $(this))
            const id = $(this).attr("scenario-id");
            console.log("ID", id);
            const selector = 'div.scenario-information[scenario-id=\'' + id + '\']';
            console.log("Selector", selector)
            $(selector).toggle();
        })

        $("span.toggleScenarioStepInformation").click(function() {
            console.log("Click", $(this))
            id = $(this).attr("scenario-id");
            console.log("ID", id);
            const selector = 'div.scenarioStepInformation[scenario-id=\'' + id + '\']';
            console.log("Selector", selector)
            $(selector).toggle();
        })
    })

</script>
