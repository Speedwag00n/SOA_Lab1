Vue.component(
    'result-table',
    {
        template:
            `
                <div>
                    <h1 class="col-xs-1 text-center">Movies</h1>
                    <table class="table table-striped table-bordered table-hover p-5 text-center">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Coordinates</th>
                            <th>Creation date</th>
                            <th>Oscars count</th>
                            <th>Golden palm count</th>
                            <th>Total box office</th>
                            <th>MPAA rating</th>
                            <th>Screen writer</th>
                            <th>Actions</th>
                        </tr>
                        <tr v-for="movie in movies">
                            <td>{{ movie.id }}</td>
                            <td>{{ movie.name }}</td>
                            <td>Id: {{ movie.coordinates.id }} (X: {{ movie.coordinates.x }}. Y: {{ movie.coordinates.y }})</td>
                            <td>{{ formatCreationDate(movie) }}</td>
                            <td>{{ movie.oscarsCount }}</td>
                            <td>{{ movie.goldenPalmCount }}</td>
                            <td>{{ movie.totalBoxOffice }}</td>
                            <td>{{ movie.mpaaRating }}</td>
                            <td v-if="movie.screenWriter">Id: {{ movie.screenWriter.id }} ({{ movie.screenWriter.name }})</td>
                            <td v-else>None</td>
                            <td>
                                <button class="btn btn-success" v-on:click="editMovie(movie)" type="submit">Edit</button>
                                <button class="btn btn-danger" v-on:click="deleteMovie(movie)" type="submit">Delete</button>
                            </td>
                        </tr>
                    </table>
                </div>
            `,

        props: ["movies"],
        methods: {
            editMovie: function (movie) {
                this.$emit('editmovie', movie);
            },
            deleteMovie: function (movie) {
                this.$emit('deletemovie', movie);
            },
            formatCreationDate: function (movie) {
                return moment(movie.creationDate).format('YYYY-MM-DD');
            }
        }
    }
);

Vue.component(
    'filters',
    {
        template:
            `
                <div>
                    <h1 class="col-xs-1 text-center">Options</h1>
                    
                    <h3>Pagination</h3>
                    <div>
                        <label for="elementsCount">Elements count:</label>
                        <input id="elementsCount" type="text" maxlength="8" v-model="elementsCount">
                        <label for="pageNumber" class="ml-5">Page number</label>
                        <input id="pageNumber" type="text" maxlength="8" v-model="pageNumber">
                    </div>
                    
                    <h3>Order by 1</h3>
                    <div>
                        <label for="orderById1">Id</label>
                        <input id="orderById1" type="radio" value="id" v-model="orderBy1">
                        <label for="orderByName1" class="pl-3">Name</label>
                        <input id="orderByName1" type="radio" value="name" v-model="orderBy1">
                        <label for="orderByCoordinates1" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates1" type="radio" value="coordinates" v-model="orderBy1">
                        <label for="orderByCreationDate1" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate1" type="radio" value="creationDate" v-model="orderBy1">     
                        <label for="orderByOscarsCount1" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount1" type="radio" value="oscarsCount" v-model="orderBy1">
                        <label for="orderByGoldenPalmCount1" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount1" type="radio" value="goldenPalmCount" v-model="orderBy1">
                        <label for="orderByTotalBoxOffice1" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice1" type="radio" value="totalBoxOffice" v-model="orderBy1">
                        <label for="orderByMPAARating1" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating1" type="radio" value="mpaaRating" v-model="orderBy1">
                        <label for="orderByScreenWriter1" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter1" type="radio" value="screenWriter" v-model="orderBy1">
                        <label for="orderByNone1" class="pl-3">None</label>
                        <input id="orderByNone1" type="radio" value="" v-model="orderBy1" checked>
                        
                        <label for="orderDirectionAsc1" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc1" type="radio" value="a" v-model="orderDirection1">
                        <label for="orderDirectionDesc1" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc1" type="radio" value="d" v-model="orderDirection1">
                        <label for="orderDirectionNone1" class="pl-3">None</label>
                        <input id="orderDirectionNone1" type="radio" value="" v-model="orderDirection1">
                    </div>
                    
                    <h3>Order by 2</h3>
                    <div>
                        <label for="orderById2">Id</label>
                        <input id="orderById2" type="radio" value="id" v-model="orderBy2">
                        <label for="orderByName2" class="pl-3">Name</label>
                        <input id="orderByName2" type="radio" value="name" v-model="orderBy2">
                        <label for="orderByCoordinates2" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates2" type="radio" value="coordinates" v-model="orderBy2">
                        <label for="orderByCreationDate2" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate2" type="radio" value="creationDate" v-model="orderBy2">     
                        <label for="orderByOscarsCount2" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount2" type="radio" value="oscarsCount" v-model="orderBy2">
                        <label for="orderByGoldenPalmCount2" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount2" type="radio" value="goldenPalmCount" v-model="orderBy2">
                        <label for="orderByTotalBoxOffice2" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice2" type="radio" value="totalBoxOffice" v-model="orderBy2">
                        <label for="orderByMPAARating2" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating2" type="radio" value="mpaaRating" v-model="orderBy2">
                        <label for="orderByScreenWriter2" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter2" type="radio" value="screenWriter" v-model="orderBy2">
                        <label for="orderByNone2" class="pl-3">None</label>
                        <input id="orderByNone2" type="radio" value="" v-model="orderBy2" checked>
                        
                        <label for="orderDirectionAsc2" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc2" type="radio" value="a" v-model="orderDirection2">
                        <label for="orderDirectionDesc2" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc2" type="radio" value="d" v-model="orderDirection2">
                        <label for="orderDirectionNone2" class="pl-3">None</label>
                        <input id="orderDirectionNone2" type="radio" value="" v-model="orderDirection2">
                    </div>
                    
                    <h3>Order by 3</h3>
                    <div>
                        <label for="orderById3">Id</label>
                        <input id="orderById3" type="radio" value="id" v-model="orderBy3">
                        <label for="orderByName3" class="pl-3">Name</label>
                        <input id="orderByName3" type="radio" value="name" v-model="orderBy3">
                        <label for="orderByCoordinates3" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates3" type="radio" value="coordinates" v-model="orderBy3">
                        <label for="orderByCreationDate3" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate3" type="radio" value="creationDate" v-model="orderBy3">     
                        <label for="orderByOscarsCount3" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount3" type="radio" value="oscarsCount" v-model="orderBy3">
                        <label for="orderByGoldenPalmCount3" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount3" type="radio" value="goldenPalmCount" v-model="orderBy3">
                        <label for="orderByTotalBoxOffice3" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice3" type="radio" value="totalBoxOffice" v-model="orderBy3">
                        <label for="orderByMPAARating3" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating3" type="radio" value="mpaaRating" v-model="orderBy3">
                        <label for="orderByScreenWriter3" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter3" type="radio" value="screenWriter" v-model="orderBy3">
                        <label for="orderByNone3" class="pl-3">None</label>
                        <input id="orderByNone3" type="radio" value="" v-model="orderBy3" checked>
                        
                        <label for="orderDirectionAsc3" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc3" type="radio" value="a" v-model="orderDirection3">
                        <label for="orderDirectionDesc3" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc3" type="radio" value="d" v-model="orderDirection3">
                        <label for="orderDirectionNone3" class="pl-3">None</label>
                        <input id="orderDirectionNone3" type="radio" value="" v-model="orderDirection3">
                    </div>
                    
                    <h3>Order by 4</h3>
                    <div>
                        <label for="orderById4">Id</label>
                        <input id="orderById4" type="radio" value="id" v-model="orderBy4">
                        <label for="orderByName4" class="pl-3">Name</label>
                        <input id="orderByName4" type="radio" value="name" v-model="orderBy4">
                        <label for="orderByCoordinates4" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates4" type="radio" value="coordinates" v-model="orderBy4">
                        <label for="orderByCreationDate4" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate4" type="radio" value="creationDate" v-model="orderBy4">     
                        <label for="orderByOscarsCount4" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount4" type="radio" value="oscarsCount" v-model="orderBy4">
                        <label for="orderByGoldenPalmCount4" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount4" type="radio" value="goldenPalmCount" v-model="orderBy4">
                        <label for="orderByTotalBoxOffice4" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice4" type="radio" value="totalBoxOffice" v-model="orderBy4">
                        <label for="orderByMPAARating4" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating4" type="radio" value="mpaaRating" v-model="orderBy4">
                        <label for="orderByScreenWriter4" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter4" type="radio" value="screenWriter" v-model="orderBy4">
                        <label for="orderByNone4" class="pl-3">None</label>
                        <input id="orderByNone4" type="radio" value="" v-model="orderBy4" checked>
                        
                        <label for="orderDirectionAsc4" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc4" type="radio" value="a" v-model="orderDirection4">
                        <label for="orderDirectionDesc4" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc4" type="radio" value="d" v-model="orderDirection4">
                        <label for="orderDirectionNone4" class="pl-3">None</label>
                        <input id="orderDirectionNone4" type="radio" value="" v-model="orderDirection4">
                    </div>
                    
                    <h3>Order by 5</h3>
                    <div>
                        <label for="orderById5">Id</label>
                        <input id="orderById5" type="radio" value="id" v-model="orderBy5">
                        <label for="orderByName5" class="pl-3">Name</label>
                        <input id="orderByName5" type="radio" value="name" v-model="orderBy5">
                        <label for="orderByCoordinates5" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates5" type="radio" value="coordinates" v-model="orderBy5">
                        <label for="orderByCreationDate5" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate5" type="radio" value="creationDate" v-model="orderBy5">     
                        <label for="orderByOscarsCount5" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount5" type="radio" value="oscarsCount" v-model="orderBy5">
                        <label for="orderByGoldenPalmCount5" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount5" type="radio" value="goldenPalmCount" v-model="orderBy5">
                        <label for="orderByTotalBoxOffice5" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice5" type="radio" value="totalBoxOffice" v-model="orderBy5">
                        <label for="orderByMPAARating5" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating5" type="radio" value="mpaaRating" v-model="orderBy5">
                        <label for="orderByScreenWriter5" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter5" type="radio" value="screenWriter" v-model="orderBy5">
                        <label for="orderByNone5" class="pl-3">None</label>
                        <input id="orderByNone5" type="radio" value="" v-model="orderBy5" checked>
                        
                        <label for="orderDirectionAsc5" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc5" type="radio" value="a" v-model="orderDirection5">
                        <label for="orderDirectionDesc5" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc5" type="radio" value="d" v-model="orderDirection5">
                        <label for="orderDirectionNone5" class="pl-3">None</label>
                        <input id="orderDirectionNone5" type="radio" value="" v-model="orderDirection5">
                    </div>
                    
                    <h3>Order by 6</h3>
                    <div>
                        <label for="orderById6">Id</label>
                        <input id="orderById6" type="radio" value="id" v-model="orderBy6">
                        <label for="orderByName6" class="pl-3">Name</label>
                        <input id="orderByName6" type="radio" value="name" v-model="orderBy6">
                        <label for="orderByCoordinates6" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates6" type="radio" value="coordinates" v-model="orderBy6">
                        <label for="orderByCreationDate6" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate6" type="radio" value="creationDate" v-model="orderBy6">     
                        <label for="orderByOscarsCount6" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount6" type="radio" value="oscarsCount" v-model="orderBy6">
                        <label for="orderByGoldenPalmCount6" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount6" type="radio" value="goldenPalmCount" v-model="orderBy6">
                        <label for="orderByTotalBoxOffice6" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice6" type="radio" value="totalBoxOffice" v-model="orderBy6">
                        <label for="orderByMPAARating6" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating6" type="radio" value="mpaaRating" v-model="orderBy6">
                        <label for="orderByScreenWriter6" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter6" type="radio" value="screenWriter" v-model="orderBy6">
                        <label for="orderByNone6" class="pl-3">None</label>
                        <input id="orderByNone6" type="radio" value="" v-model="orderBy6" checked>
                        
                        <label for="orderDirectionAsc6" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc6" type="radio" value="a" v-model="orderDirection6">
                        <label for="orderDirectionDesc6" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc6" type="radio" value="d" v-model="orderDirection6">
                        <label for="orderDirectionNone6" class="pl-3">None</label>
                        <input id="orderDirectionNone6" type="radio" value="" v-model="orderDirection6">
                    </div>
                    
                    <h3>Order by 7</h3>
                    <div>
                        <label for="orderById7">Id</label>
                        <input id="orderById7" type="radio" value="id" v-model="orderBy7">
                        <label for="orderByName7" class="pl-3">Name</label>
                        <input id="orderByName7" type="radio" value="name" v-model="orderBy7">
                        <label for="orderByCoordinates7" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates7" type="radio" value="coordinates" v-model="orderBy7">
                        <label for="orderByCreationDate7" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate7" type="radio" value="creationDate" v-model="orderBy7">     
                        <label for="orderByOscarsCount7" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount7" type="radio" value="oscarsCount" v-model="orderBy7">
                        <label for="orderByGoldenPalmCount7" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount7" type="radio" value="goldenPalmCount" v-model="orderBy7">
                        <label for="orderByTotalBoxOffice7" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice7" type="radio" value="totalBoxOffice" v-model="orderBy7">
                        <label for="orderByMPAARating7" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating7" type="radio" value="mpaaRating" v-model="orderBy7">
                        <label for="orderByScreenWriter7" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter7" type="radio" value="screenWriter" v-model="orderBy7">
                        <label for="orderByNone7" class="pl-3">None</label>
                        <input id="orderByNone7" type="radio" value="" v-model="orderBy7" checked>
                        
                        <label for="orderDirectionAsc7" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc7" type="radio" value="a" v-model="orderDirection7">
                        <label for="orderDirectionDesc7" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc7" type="radio" value="d" v-model="orderDirection7">
                        <label for="orderDirectionNone7" class="pl-3">None</label>
                        <input id="orderDirectionNone7" type="radio" value="" v-model="orderDirection7">
                    </div>
                    
                    <h3>Order by 8</h3>
                    <div>
                        <label for="orderById8">Id</label>
                        <input id="orderById8" type="radio" value="id" v-model="orderBy8">
                        <label for="orderByName8" class="pl-3">Name</label>
                        <input id="orderByName8" type="radio" value="name" v-model="orderBy8">
                        <label for="orderByCoordinates8" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates8" type="radio" value="coordinates" v-model="orderBy8">
                        <label for="orderByCreationDate8" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate8" type="radio" value="creationDate" v-model="orderBy8">     
                        <label for="orderByOscarsCount8" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount8" type="radio" value="oscarsCount" v-model="orderBy8">
                        <label for="orderByGoldenPalmCount8" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount8" type="radio" value="goldenPalmCount" v-model="orderBy8">
                        <label for="orderByTotalBoxOffice8" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice8" type="radio" value="totalBoxOffice" v-model="orderBy8">
                        <label for="orderByMPAARating8" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating8" type="radio" value="mpaaRating" v-model="orderBy8">
                        <label for="orderByScreenWriter8" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter8" type="radio" value="screenWriter" v-model="orderBy8">
                        <label for="orderByNone8" class="pl-3">None</label>
                        <input id="orderByNone8" type="radio" value="" v-model="orderBy8" checked>
                        
                        <label for="orderDirectionAsc8" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc8" type="radio" value="a" v-model="orderDirection8">
                        <label for="orderDirectionDesc8" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc8" type="radio" value="d" v-model="orderDirection8">
                        <label for="orderDirectionNone8" class="pl-3">None</label>
                        <input id="orderDirectionNone8" type="radio" value="" v-model="orderDirection8">
                    </div>
                    
                    <h3>Order by 9</h3>
                    <div>
                        <label for="orderById9">Id</label>
                        <input id="orderById9" type="radio" value="id" v-model="orderBy9">
                        <label for="orderByName9" class="pl-3">Name</label>
                        <input id="orderByName9" type="radio" value="name" v-model="orderBy9">
                        <label for="orderByCoordinates9" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates9" type="radio" value="coordinates" v-model="orderBy9">
                        <label for="orderByCreationDate9" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate9" type="radio" value="creationDate" v-model="orderBy9">     
                        <label for="orderByOscarsCount9" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount9" type="radio" value="oscarsCount" v-model="orderBy9">
                        <label for="orderByGoldenPalmCount9" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount9" type="radio" value="goldenPalmCount" v-model="orderBy9">
                        <label for="orderByTotalBoxOffice9" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice9" type="radio" value="totalBoxOffice" v-model="orderBy9">
                        <label for="orderByMPAARating9" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating9" type="radio" value="mpaaRating" v-model="orderBy9">
                        <label for="orderByScreenWriter9" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter9" type="radio" value="screenWriter" v-model="orderBy9">
                        <label for="orderByNone9" class="pl-3">None</label>
                        <input id="orderByNone9" type="radio" value="" v-model="orderBy9" checked>
                        
                        <label for="orderDirectionAsc9" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc9" type="radio" value="a" v-model="orderDirection9">
                        <label for="orderDirectionDesc9" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc9" type="radio" value="d" v-model="orderDirection9">
                        <label for="orderDirectionNone9" class="pl-3">None</label>
                        <input id="orderDirectionNone9" type="radio" value="" v-model="orderDirection9">
                    </div>
                    
                    <h3>Filter</h3>
                    <div>
                        <label for="filterById">Id</label>
                        <input id="filterById" type="text" maxlength="8" v-model="filterById">
                    </div>
                    <div>
                        <label for="filterByName">Name</label>
                        <input id="filterByName" type="text" maxlength="256" v-model="filterByName">
                    </div>
                    <div>
                        <label for="filterByCoordinates">Coordinates</label>
                        <input id="filterByCoordinates" type="text" maxlength="8" v-model="filterByCoordinates">
                    </div>
                    <div>
                        <label for="filterByCreationDate">Creation date</label>
                        <input id="filterByCreationDate" type="text" maxlength="10" v-model="filterByCreationDate">
                        
                        <label for="filterByCreationDateActionLess" class="pl-5"><</label>
                        <input id="filterByCreationDateActionLess" type="radio" value="<" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionEqual" class="pl-5">==</label>
                        <input id="filterByCreationDateActionEqual" type="radio" value="==" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionGreater" class="pl-5">\></label>
                        <input id="filterByCreationDateActionGreater" type="radio" value=">" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByCreationDateActionLessEqual" type="radio" value="<=" v-model="filterByCreationDateAction" name="filterByCreationDateAction">     
                        <label for="filterByCreationDateActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByCreationDateActionGreaterEqual" type="radio" value=">=" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                    </div>
                    <div>
                        <label for="filterByOscarCount">Oscars count</label>
                        <input id="filterByOscarCount" type="text" maxlength="8" v-model="filterByOscarCount">
                        
                        <label for="filterByOscarCountActionLess" class="pl-5"><</label>
                        <input id="filterByOscarCountActionLess" type="radio" value="<" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                        <label for="filterByOscarCountActionEqual" class="pl-5">==</label>
                        <input id="filterByOscarCountActionEqual" type="radio" value="==" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                        <label for="filterByOscarCountActionGreater" class="pl-5">\></label>
                        <input id="filterByOscarCountActionGreater" type="radio" value=">" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                        <label for="filterByOscarCountActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByOscarCountActionLessEqual" type="radio" value="<=" v-model="filterByOscarCountAction" name="filterByOscarCountAction">     
                        <label for="filterByOscarCountActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByOscarCountActionGreaterEqual" type="radio" value=">=" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                    </div>
                    <div>
                        <label for="filterByGoldenPalmCount">Golden palm count</label>
                        <input id="filterByGoldenPalmCount" type="text" maxlength="8" v-model="filterByGoldenPalmCount">
                        
                        <label for="filterByGoldenPalmCountActionLess" class="pl-5"><</label>
                        <input id="filterByGoldenPalmCountActionLess" type="radio" value="<" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                        <label for="filterByGoldenPalmCountActionEqual" class="pl-5">==</label>
                        <input id="filterByGoldenPalmCountActionEqual" type="radio" value="==" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                        <label for="filterByGoldenPalmCountActionGreater" class="pl-5">\></label>
                        <input id="filterByGoldenPalmCountActionGreater" type="radio" value=">" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                        <label for="filterByGoldenPalmCountActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByGoldenPalmCountActionLessEqual" type="radio" value="<=" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">     
                        <label for="filterByGoldenPalmCountActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByGoldenPalmCountActionGreaterEqual" type="radio" value=">=" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                    </div>
                    <div>
                        <label for="filterByTotalBoxOffice">Total box office</label>
                        <input id="filterByTotalBoxOffice" type="text" maxlength="12" v-model="filterByTotalBoxOffice">
                        
                        <label for="filterByTotalBoxOfficeActionLess" class="pl-5"><</label>
                        <input id="filterByTotalBoxOfficeActionLess" type="radio" value="<" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                        <label for="filterByTotalBoxOfficeActionEqual" class="pl-5">==</label>
                        <input id="filterByTotalBoxOfficeActionEqual" type="radio" value="==" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                        <label for="filterByTotalBoxOfficeActionGreater" class="pl-5">\></label>
                        <input id="filterByTotalBoxOfficeActionGreater" type="radio" value=">" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                        <label for="filterByTotalBoxOfficeActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByTotalBoxOfficeActionLessEqual" type="radio" value="<=" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">     
                        <label for="filterByTotalBoxOfficeActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByTotalBoxOfficeActionGreaterEqual" type="radio" value=">=" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                    </div>
                    <div>
                        <label for="filterByRating">MPAA Rating</label>
                        
                        <label for="filterByRatingG" class="pl-5">G</label>
                        <input id="filterByRatingG" type="radio" value="G" v-model="filterByRating" name="filterByRating">
                        <label for="filterByRatingPG" class="pl-5">PG</label>
                        <input id="filterByRatingPG" type="radio" value="PG" v-model="filterByRating" name="filterByRating">
                        <label for="filterByRatingPG13" class="pl-5">PG_13</label>
                        <input id="filterByRatingPG13" type="radio" value="PG_13" v-model="filterByRating" name="filterByRating">
                        <label for="filterByRatingNC17" class="pl-5">NC_17</label>
                        <input id="filterByRatingNC17" type="radio" value="NC_17" v-model="filterByRating" name="filterByRating">
                    </div>
                    <div>
                        <label for="filterByScreenWriter">Screen writer</label>
                        <input id="filterByScreenWriter" type="text" maxlength="8" v-model="filterByScreenWriter">
                    </div>
                    
                    <button class="btn btn-info" v-on:click="filter()" type="submit">Filter</button>
                </div>
            `,

        data: function() {
            return {
                elementsCount: '',
                pageNumber: '',
                orderBy1: '',
                orderDirection1: '',
                orderBy2: '',
                orderDirection2: '',
                orderBy3: '',
                orderDirection3: '',
                orderBy4: '',
                orderDirection4: '',
                orderBy5: '',
                orderDirection5: '',
                orderBy6: '',
                orderDirection6: '',
                orderBy7: '',
                orderDirection7: '',
                orderBy8: '',
                orderDirection8: '',
                orderBy9: '',
                orderDirection9: '',
                filterById: '',
                filterByName: '',
                filterByCoordinates: '',
                filterByCreationDate: '',
                filterByCreationDateAction: '',
                filterByOscarCount: '',
                filterByOscarCountAction: '',
                filterByGoldenPalmCount: '',
                filterByGoldenPalmCountActionAction: '',
                filterByTotalBoxOffice: '',
                filterByTotalBoxOfficeAction: '',
                filterByRating: '',
                filterByScreenWriter: ''
            }
        },
        methods: {
            filter: function () {
                this.$emit('filter', {
                    'elementsCount': this.elementsCount,
                    'pageNumber': this.pageNumber,
                    'orderBy1': this.orderBy1,
                    'orderDirection1': this.orderDirection1,
                    'orderBy2': this.orderBy2,
                    'orderDirection2': this.orderDirection2,
                    'orderBy3': this.orderBy3,
                    'orderDirection3': this.orderDirection3,
                    'orderBy4': this.orderBy4,
                    'orderDirection4': this.orderDirection4,
                    'orderBy5': this.orderBy5,
                    'orderDirection5': this.orderDirection5,
                    'orderBy6': this.orderBy6,
                    'orderDirection6': this.orderDirection6,
                    'orderBy7': this.orderBy7,
                    'orderDirection7': this.orderDirection7,
                    'orderBy8': this.orderBy8,
                    'orderDirection8': this.orderDirection8,
                    'orderBy9': this.orderBy9,
                    'orderDirection9': this.orderDirection9,
                    'filterById': this.filterById,
                    'filterByName': this.filterByName,
                    'filterByCoordinates': this.filterByCoordinates,
                    'filterByCreationDate': this.filterByCreationDate,
                    'filterByCreationDateAction': this.filterByCreationDateAction,
                    'filterByOscarCount': this.filterByOscarCount,
                    'filterByOscarCountAction': this.filterByOscarCountAction,
                    'filterByGoldenPalmCount': this.filterByGoldenPalmCount,
                    'filterByGoldenPalmCountAction': this.filterByGoldenPalmCountAction,
                    'filterByTotalBoxOffice': this.filterByTotalBoxOffice,
                    'filterByTotalBoxOfficeAction': this.filterByTotalBoxOfficeAction,
                    'filterByRating': this.filterByRating,
                    'filterByScreenWriter': this.filterByScreenWriter
                });
            }
        }
    }
);

Vue.component(
    'add-movie',
    {
        template:
            `
            <div>
                <h1 class="col-xs-1 text-center">Add/update movie</h1>
                <div>
                    <label for="id">Id</label>
                    <input id="id" type="text" maxlength="8" v-model="id">
                </div>
                <div>
                    <label for="name">Name</label>
                    <input id="name" type="text" maxlength="256" v-model="name">
                </div>
                <div>
                    <label for="coordinates">Coordinates</label>
                    <input id="coordinates" type="text" maxlength="8" v-model="coordinates">
                </div>
                <div>
                    <label for="creationDate">Creation date</label>
                    <input id="creationDate" type="text" maxlength="10" v-model="creationDate">
                </div>
                <div>
                    <label for="oscarsCount">Oscars count</label>
                    <input id="oscarsCount" type="text" maxlength="8" v-model="oscarsCount">
                </div>
                <div>
                    <label for="goldenPalmCount">Golden palm count</label>
                    <input id="goldenPalmCount" type="text" maxlength="8" v-model="goldenPalmCount">
                </div>
                <div>
                    <label for="totalBoxOffice">Total box office</label>
                    <input id="totalBoxOffice" type="text" maxlength="12" v-model="totalBoxOffice">
                </div>
                <div>
                    <label for="mpaaRating">MPAA Rating</label>
                    
                    <label for="filterByRatingG" class="pl-5">G</label>
                    <input id="filterByRatingG" type="radio" value="G" v-model="mpaaRating" name="mpaaRating">
                    <label for="filterByRatingPG" class="pl-5">PG</label>
                    <input id="filterByRatingPG" type="radio" value="PG" v-model="mpaaRating" name="mpaaRating">
                    <label for="filterByRatingPG13" class="pl-5">PG_13</label>
                    <input id="filterByRatingPG13" type="radio" value="PG_13" v-model="mpaaRating" name="mpaaRating">
                    <label for="filterByRatingNC17" class="pl-5">NC_17</label>
                    <input id="filterByRatingNC17" type="radio" value="NC_17" v-model="mpaaRating" name="mpaaRating">
                </div>
                <div>
                    <label for="screenWriter">Screen writer</label>
                    <input id="screenWriter" type="text" maxlength="8" v-model="screenWriter">
                </div>
                
                <button v-if="id" class="btn btn-info" v-on:click="addMovie()" type="submit">Update</button>
                <button v-else class="btn btn-info" v-on:click="addMovie()" type="submit">Add</button>
            </div>
            `,

        props: ["personslist", "coordinateslist"],
        data: function() {
            return {
                id: '',
                name: '',
                coordinates: '',
                oscarsCount: '',
                goldenPalmCount: '',
                totalBoxOffice: '',
                mpaaRating: '',
                screenWriter: ''
            }
        },
        methods: {
            addMovie: function () {
                let movie = {
                    'name': this.name,
                    'coordinates': this.coordinates,
                    'creationDate': this.creationDate,
                    'oscarsCount': this.oscarsCount,
                    'goldenPalmCount': this.goldenPalmCount
                }
                if (this.totalBoxOffice) {
                    movie.totalBoxOffice = this.totalBoxOffice
                }
                if (this.mpaaRating) {
                    movie.mpaaRating = this.mpaaRating
                }
                if (this.screenWriter) {
                    movie.screenWriter = this.screenWriter
                }
                if (this.id) {
                    movie.id = this.id
                }

                this.$emit('addmovie', movie);
            },
            updateFields: function(movie) {
                this.id = movie.id
                this.name = movie.name
                this.coordinates = movie.coordinates.id
                this.creationDate = moment(movie.creationDate).format('YYYY-MM-DD')
                this.oscarsCount = movie.oscarsCount
                this.goldenPalmCount = movie.goldenPalmCount
                this.totalBoxOffice = movie.totalBoxOffice
                this.mpaaRating = movie.mpaaRating
                this.screenWriter = movie.screenWriter.id
            }
        }
    }
);

Vue.component(
    'additional-actions',
    {
        template:
            `
            <div>
                <h1 class="col-xs-1 text-center">Avrg golden palm count</h1>
                <div>{{ goldenpalmcountavrg }}</div>
                <button class="btn btn-info" v-on:click="updateAvrg()" type="submit">Update avrg goldenPalmCount</button>
            </div>
            `,

        props: ["goldenpalmcountavrg"],
        methods: {
            updateAvrg: function () {
                this.$emit('calcavrg');
            }
        }
    }
);